package org.techtown.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Infl_savedActivity extends AppCompatActivity {

    DBHelper dbHelper;

    TextView viewResult4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infl_saved);

        dbHelper = new DBHelper(Infl_savedActivity.this, 1);

        Intent intent = getIntent();

        String log_userid = intent.getExtras().getString("log_userid");

        viewResult4 = (TextView) findViewById(R.id.textView19);
        viewResult4.setText(dbHelper.Inf_Scoreboard_getResult(log_userid));

        Button back_btn = (Button)findViewById(R.id.button8);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setResult(Activity.RESULT_OK);
                finish();

            }
        });
    }
}