package com.example.guswn_000.mengmo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guswn_000 on 2017-06-12.
 */

public class DbOpenHelper
{
    private static final String DATABASE_NAME = "MengmojangDB";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String sql1 = "Create table recpath (" +
                    "path text primary key);";
            String sql2 = "Create table imgpath (" +
                    "path text primary key);";
            String sql3 = "Create table txtpath (" +
                    "path text primary key);";
            db.execSQL(sql1);
            db.execSQL(sql2);
            db.execSQL(sql3);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("Drop table if exists recpath");
            db.execSQL("Drop table if exists imgpath");
            db.execSQL("Drop table if exists txtpath");
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context) {
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException
    {
        mDBHelper = new DatabaseHelper(mCtx,DATABASE_NAME,null,DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        mDB.close();
    }

    public void INSERTrecpath(String path)
    {
        ContentValues values = new ContentValues();
        values.put("path",path);
        mDB.insert("recpath",null,values);
    }

    public void INSERTimgpath(String path)
    {
        ContentValues values = new ContentValues();
        values.put("path",path);
        mDB.insert("imgpath",null,values);
    }

    public void INSERTtxtpath(String path)
    {
        ContentValues values = new ContentValues();
        values.put("path",path);
        mDB.insert("txtpath",null,values);
    }

    public void deleterecpath(String path)
    {
        mDB.delete("recpath","path = '" + path+"'",null);
    }

    public void deleteimgpath(String path)
    {
        mDB.delete("imgpath","path = '" + path+"'",null);
    }

    public void deletetxtpath(String path)
    {
        mDB.delete("txtpath","path = '" + path+"'",null);
    }

    public Cursor getAllrecpath()
    {
        return mDB.query("recpath",null,null,null,null,null,"path desc");
    }

    public Cursor getAllimgpath()
    {
        return mDB.query("imgpath",null,null,null,null,null,"path desc");
    }

    public Cursor getAlltxtpath()
    {
        return mDB.query("txtpath",null,null,null,null,null,"path desc");
    }










}
