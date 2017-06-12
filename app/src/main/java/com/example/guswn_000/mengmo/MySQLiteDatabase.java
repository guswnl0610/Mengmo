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
                "path text not null);";
        String createimg = "Create table imagepath (" +
                "id integer primary key autoincrement," +
                "path text not null);";
        String createtxt = "Create table textpath (" +
                "id integer primary key autoincrement," +
                "path text not null);";
        db.execSQL(createrec);
        db.execSQL(createimg);
        db.execSQL(createtxt);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String sql = "drop table if exists recordpath";
        String sql2 = "drop table if exists imagepath";
        String sql3 = "drop table if exists textpath";
        onCreate(db);
    }
}
