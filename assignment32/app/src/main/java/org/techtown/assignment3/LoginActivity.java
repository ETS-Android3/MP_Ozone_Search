package org.techtown.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SIGNUP = 101;
    public static final int REQUEST_CODE_LOGIN = 102;
    public static final int REQUEST_CODE_SETTING = 103;

    TextView viewResult;
    EditText ED_ID_login,ED_PW_login;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(LoginActivity.this, 1);

        ED_ID_login = (EditText)findViewById(R.id.textView3);
        ED_PW_login = (EditText)findViewById(R.id.editTextTextPassword2);

        Button create_button = findViewById(R.id.button3);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SIGNUP);
            }
        });

        Button login_button = findViewById(R.id.button2);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean res = dbHelper.login(ED_ID_login.getText().toString(), ED_PW_login.getText().toString());
                if( res == true){
                    Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), NoticeBActivity.class);
                    intent.putExtra("log_userid", ED_ID_login.getText().toString());
                    startActivity(intent);
                }
                else{
                    showDialog();
                    //Toast.makeText(getApplicationContext(), "로그인 실패!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ImageButton setting_button = findViewById(R.id.imagebutton7);
        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SETTING);
            }
        });



    }
    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle("알림")
                .setMessage("존재하지 않는 아이디나 잘못된 페스워드를 입력했습니다. 다시 확인해주세요.")
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