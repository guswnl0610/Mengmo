package com.example.guswn_000.mengmo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guswn_000 on 2017-06-12.
 */

public class MySQLiteDatabase extends SQLiteOpenHelper {
    public MySQLiteDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createrec = "Create table recordpath (" +
                "id integer primary key autoincrement," +
                "title text not null," +
                "date text);";
        String createimg = "Create table images (" +
                "id integer primary key autoincrement," +
                "title text not null," +
                "date text);";
        String createtxt = "Create table texts (" +
                "id integer primary key autoincrement," +
                "title text not null," +
                "date text);";
        db.execSQL(createrec);
        db.execSQL(createimg);
        db.execSQL(createtxt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String sql = "drop table if exists records";
        String sql2 = "drop table if exists images";
        String sql3 = "drop table if exists texts";
        onCreate(db);
    }
}
