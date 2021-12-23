package org.techtown.assignment3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "test.db"; // DBHelper 생성자

    public DBHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    } // Person Table 생성

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Person(ID TEXT, PW TEXT)");
        db.execSQL("CREATE TABLE Inf_Scoreboard(ID TEXT, OZONE TEXT, P_NAME TEXT, REAL_TIME TEXT)");
    } // Person Table Upgrade

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Person"); onCreate(db);
    } // Person Table 데이터 입력

    public void insert(String ID, String PW) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO Person VALUES('" + ID + "', '" + PW + "')");
        db.close();
    } // Person Table 데이터 수정

    public void insert_scoreboard(String ID, String OZONE, String P_NAME, String REAL_TIME) {
        SQLiteDatabase db = getWritableDatabase();
        //db.execSQL("CREATE TABLE Inf_Scoreboard(ID TEXT, OZONE TEXT, P_NAME TEXT, REAL_TIME TEXT)");
        db.execSQL("INSERT INTO Inf_Scoreboard VALUES('" + ID + "', '" + OZONE + "','" + P_NAME + "', '" + REAL_TIME + "')");
        db.close();
    } // Person Table 데이터 수정

    public void Update(String ID, String PW) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE Person SET ID = " + ID + ", PW = '" + PW + "'");
        db.close();
    } // Person Table 데이터 삭제

    public void Delete(String ID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE Person WHERE ID = '" + ID + "'");
        db.close();
    } // Person Table 조회

    // Person Table 조회
    public String getResult() { // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();

        String result = ""; // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM Person", null);
        int num=0;
        while (cursor.moveToNext()) {
            result += "Num: "+ num +", ID : " + cursor.getString(0) +", PW : " + cursor.getString(1) + "\n";
            num+=1;
        }
        return result;
    }

    // Inf_Scoreboard Table 조회
    public String Inf_Scoreboard_getResult(String log_id) { // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();

        String result = ""; // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM Inf_Scoreboard", null);
        int num=0;
        while (cursor.moveToNext()) {
            if(log_id.equals(cursor.getString(0))){
                result += "Num: "+ num +", ID : " + cursor.getString(0) +", OZONE : " + cursor.getString(1) +", P_NAME : " + cursor.getString(2)+", TIME : " + cursor.getString(3)+"\n";
                num+=1;
            }
        }
        return result;
    }

    public boolean check_duplicate(String EditText) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Person", null);
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(EditText)){
                return false;
            }
        }
        return true;
    }

    public boolean login(String E_ID, String E_PW) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Person", null);
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equals(E_ID) && cursor.getString(1).equals(E_PW)){
                return true;
            }
        }
        return false;
    }

}

