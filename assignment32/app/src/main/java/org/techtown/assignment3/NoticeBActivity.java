package org.techtown.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class NoticeBActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_MAIN = 104;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_bactivity);

        CheckBox CB = (CheckBox)findViewById(R.id.checkBox2);

        Button check_bt = (Button)findViewById(R.id.button7);



        Intent intent = getIntent();

        String log_userid = intent.getExtras().getString("log_userid");


        check_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CB.isChecked() == true){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("log_userid", log_userid.toString());
                    startActivity(intent);
                }
                else{
                    showDialog();
                }



            }
        });

    }
    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(NoticeBActivity.this)
                .setTitle("알림")
                .setMessage("해당 글을 숙지하고 체크박스를 클릭해주세요.")
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