package org.techtown.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_DATABASE = 104;

    EditText CODE;

    String str_code = "root";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        CODE = (EditText)findViewById(R.id.editTextTextPassword);



        Button db_button = findViewById(R.id.button5);
        db_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CODE.getText().toString().equals(str_code)){
                    Toast.makeText(getApplicationContext(), "Auth Succeed", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), dbActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_DATABASE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Auth Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}