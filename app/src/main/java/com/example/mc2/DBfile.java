package com.example.mc2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;

public class DBfile extends SQLiteOpenHelper {



    Context context;
    public DBfile(Context context) {
        super(context,  "Student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
DB.execSQL("create Table Studentdetails(name TEXT primary key, age INT , gender TEXT,photo blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int il) {
        DB.execSQL("drop Table if exists Studentdetails");

    }

    public Boolean insertuserdata(String name, int age, String gender,byte[] img){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put("name",name);
        contentValues.put("age",age);
        contentValues.put("gender",gender);
        contentValues.put("photo",img);

        long reslt= DB.insert("Studentdetails",null,contentValues);

        if (reslt == -1) {
            return false;
        }else{
            return true;
        }
    }


    public Cursor getdata(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor= DB.rawQuery("Select * from Studentdetails", null);


        return cursor;

    }


}
