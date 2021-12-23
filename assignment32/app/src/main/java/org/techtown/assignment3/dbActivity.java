package org.techtown.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;

public class dbActivity extends AppCompatActivity {

    DBHelper dbHelper;

    TextView viewResult3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        dbHelper = new DBHelper(dbActivity.this, 1);


        viewResult3 = (TextView) findViewById(R.id.textView11);
        viewResult3.setText(dbHelper.getResult());


    }
}