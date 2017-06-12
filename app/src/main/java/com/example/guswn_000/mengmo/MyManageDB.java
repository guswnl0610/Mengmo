package com.example.guswn_000.mengmo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by guswn_000 on 2017-06-12.
 */

public class MyManageDB
{
    private static MySQLiteDatabase database = null;
    private static SQLiteDatabase mengmoDB = null;
    private static MyManageDB mInstance = null;

    public final static MyManageDB getmInstance(Context context)
    {
        if(mInstance == null) mInstance = new MyManageDB(context);
        return mInstance;
    }

    MyManageDB(Context context)
    {
        database = new MySQLiteDatabase(context, "mengmoDB",null,1);
        mengmoDB = database.getWritableDatabase();
    }

    public void INSERTrecpath(String path)
    {
        String sql = "Insert into recordpath values (null, '" + path + "');";
        mengmoDB.execSQL(sql);
    }

    public void INSERTimgpath(String path)
    {
        String sql = "Insert into imagepath values (null, '" + path + "');";
        mengmoDB.execSQL(sql);
    }

    public void INSERTtxtpath(String path)
    {
        String sql = "Insert into textpath values (null, '" + path + "');";
        mengmoDB.execSQL(sql);
    }

    public Cursor execSELECTquery(String sql)
    {
        Cursor cursor = mengmoDB.rawQuery(sql,null);
        return cursor;
    }



}
