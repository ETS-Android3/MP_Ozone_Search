package org.techtown.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class SignupActivity extends AppCompatActivity {



    DBHelper dbHelper;

    Button btnSave;
    EditText ED_ID,ED_PW;

    TextView viewResult2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnSave = (Button) findViewById(R.id.button4);

        ED_ID = (EditText)findViewById(R.id.textView7);
        ED_PW = (EditText)findViewById(R.id.editTextTextPassword3);


        dbHelper = new DBHelper(SignupActivity.this, 1);



        Button create_acc_button = findViewById(R.id.button4);
        //SAVE 버튼 클릭시 acc 데이터 전송하고 MAIN으로 Connect
        create_acc_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                boolean res = dbHelper.check_duplicate(ED_ID.getText().toString());
                if(res == false){
                    showDialog();
                    //Toast.makeText(getApplicationContext(), "동일한 ID 존재!", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(ED_ID.getText().toString().equals("") || ED_PW.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "ID 또는 PW를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent();
                        Toast.makeText(getApplicationContext(), "성공적으로 회원가입되셨습니다.", Toast.LENGTH_SHORT).show();
                        dbHelper.insert(ED_ID.getText().toString(), ED_PW.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                }

                //Toast.makeText(getApplicationContext(), "ID:"+ED_ID.getText().toString()+", Pw:"+ED_PW.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(SignupActivity.this)
                .setTitle("알림")
                .setMessage("중복되는 아이디가 존재합니다. 다른 아이디를 입력해주세요.")
                .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(SignupActivity.this, "안 끔", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

}