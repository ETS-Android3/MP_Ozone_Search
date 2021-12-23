package org.techtown.assignment3;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;




public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_DASH = 200;

    EditText edit;
    TextView text, ozone, p_name, res_txt, real_time;
    XmlPullParser xpp;

    String data;

    DBHelper dbHelper;

    int count = 0;
    boolean alert = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit= (EditText)findViewById(R.id.edit);
        text= (TextView)findViewById(R.id.result);

        ozone= (TextView)findViewById(R.id.textView12);
        p_name= (TextView)findViewById(R.id.textView14);
        res_txt= (TextView)findViewById(R.id.textView15);
        real_time= (TextView)findViewById(R.id.textView16);

        TextView log_id = (TextView)findViewById(R.id.textView20);

        Intent intent = getIntent();

        String log_userid = intent.getExtras().getString("log_userid");
        log_id.setText(log_userid+"님");

        dbHelper = new DBHelper(MainActivity.this, 1);

        Button save_btn = (Button)findViewById(R.id.button6);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alert == false){
                    dbHelper.insert_scoreboard(log_userid.toString(), ozone.getText().toString(),p_name.getText().toString(), real_time.getText().toString());

                    Intent intent = new Intent(getApplicationContext(), Infl_savedActivity.class);
                    intent.putExtra("log_userid", log_userid.toString());

                    startActivity(intent);
                }
                else{
                    showDialog();
                }


            }
        });

        Button check_btn = (Button)findViewById(R.id.button9);
        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), Infl_savedActivity.class);
                intent.putExtra("log_userid", log_userid.toString());
                startActivityForResult(intent, REQUEST_CODE_DASH);

            }
        });

    }
    public void mOnClick(View v){
        switch (v.getId()){
            case R.id.button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data = getXmlData();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(data);

                                SimpleDateFormat time;
                                Date dt;
                                time = new SimpleDateFormat("hh:mm:ss a");

                                real_time.setText(time.format(new Date()));
                                p_name.setText(edit.getText().toString());
                                res_txt.setText("1");

                                if (alert == true){
                                    showDialog();
                                }

                            }
                        });
                    }
                }).start();
                break;
        }
    }
    String getXmlData(){
        StringBuffer buffer=new StringBuffer();
        String str= edit.getText().toString();//EditText에 작성된 Text얻어오기
        String location = URLEncoder.encode(str);
        String query="%EC%A0%84%EB%A0%A5%EB%A1%9C";

        String queryUrl="http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?serviceKey=a8HmMYNt4AprIFh50ZEdro19Os97CppuZcfH2vyAjvNdSt9SPms7rIjg5HwUGrfcDwWQmlGYpAOzcGB9Mozqqg%3D%3D&returnType=xml&numOfRows=100&pageNo=1&sidoName=%EC%84%9C%EC%9A%B8&ver=1.0";
        try{
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:

                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("item"));// 첫번째 검색결과

                        else if(tag.equals("stationName")){

                            xpp.next();
                            if(str.equals(xpp.getText())) {
                                //buffer.append("구명 : ");

                                //buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                                //buffer.append("\n"); //줄바꿈 문자 추가
                                for (int i = 0; i < 8; i++){
                                    xpp.next();
                                }


                                //buffer.append("오존 수치 : ");
                                ozone.setText(xpp.getText().toString());
                                //buffer.append(xpp.getText());
                                count += 1;
                            }

                        }

                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        //if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
            //buffer.append("잘못된 입력입니다.\n");
        }

        if(count == 0){
            //buffer.append("잘못된 입력입니다. _____구로 입력해주세요.\n");
            alert = true;
        }
        else{
            SimpleDateFormat time;
            Date dt;
            time = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");

            //res_txt.setText(String.valueOf(count));
            //buffer.append("\n검색결과 "+count+"건 입니다.\n");

            buffer.append(time.format(new Date()));
            alert = false;
        }
        count = 0;
        //buffer.append("파싱 끝\n");
        String d= "";
        return d; //buffer.toString();//StringBuffer 문자열 객체 반환

    }//getXmlData method....

    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MainActivity.this)
                .setTitle("알림")
                .setMessage("검색창에 ○○구 또는 존재하는 서울 구명을 입력해주세요(지방 검색 불가)")
                .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(LoginActivity.this, "안 끔", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }



}