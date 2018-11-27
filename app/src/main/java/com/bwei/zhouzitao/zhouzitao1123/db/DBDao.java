package com.bwei.zhouzitao.zhouzitao1123.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBDao {


    private final SQLiteDatabase db;

    public DBDao(Context context) {
        DBHelper dbHelper = new DBHelper(context, "why.db", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    public long add(String data){
        ContentValues values = new ContentValues();
        values.put("data",data);
        return db.insert("why",null,values);
    }

    public String query(){
        String data = "";
        Cursor cursor = db.query("why", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            data = cursor.getString(cursor.getColumnIndex("data"));
        }
        return data;
    }
}
