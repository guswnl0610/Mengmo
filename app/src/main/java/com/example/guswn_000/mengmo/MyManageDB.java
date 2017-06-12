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

    public void INSERTrecords(String title, String date)
    {
        String sql = "Insert into records values (null, '" + title + "','" + date + "');";
        mengmoDB.execSQL(sql);
    }

    public void INSERTimages(String title, String date)
    {
        String sql = "Insert into images values (null, '" + title + "','" + date + "');";
        mengmoDB.execSQL(sql);
    }

    public void INSERTtexts(String title, String date)
    {
        String sql = "Insert into texts values (null, '" + title + "','" + date + "');";
        mengmoDB.execSQL(sql);
    }

    public Cursor execSELECTquery(String sql)
    {
        Cursor cursor = mengmoDB.rawQuery(sql,null);
        return cursor;
    }

    public void DELETErecords(String title)
    {
        String sql = "Delete from records where title = '" + title + "';";
        mengmoDB.execSQL(sql);
    }

    public void DELETEimages(String title)
    {
        String sql = "Delete from images where title = '" + title + "';";
        mengmoDB.execSQL(sql);
    }

    public void DELETEtexts(String title)
    {
        String sql = "Delete from texts where title = '" + title + "';";
        mengmoDB.execSQL(sql);
    }

    public void UPDATErecords(String origintitle,String origindate,String newtitle,String newdate)
    {
        String sql = "Update records set title = '" + newtitle + "', date ='" + newdate +"' where" +
                "title = '" + origintitle + "', date ='" + origindate + "';";
        mengmoDB.execSQL(sql);
    }
    public void UPDATEimages(String origintitle,String origindate,String newtitle,String newdate)
    {
        String sql = "Update images set title = '" + newtitle + "', date ='" + newdate +"' where" +
                "title = '" + origintitle + "', date ='" + origindate + "';";
        mengmoDB.execSQL(sql);
    }
    public void UPDATEtexts(String origintitle,String origindate,String newtitle,String newdate)
    {
        String sql = "Update texts set title = '" + newtitle + "', date ='" + newdate +"' where" +
                "title = '" + origintitle + "', date ='" + origindate + "';";
        mengmoDB.execSQL(sql);
    }


}
