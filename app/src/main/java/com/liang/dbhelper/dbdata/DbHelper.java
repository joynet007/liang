package com.liang.dbhelper.dbdata;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liang on 16/3/22.
 */
public class DbHelper extends SQLiteOpenHelper{


    private static String db_name = "ep.db";
    private static int db_version = 1;

    public DbHelper(Context context) {
        super(context, db_name, null, db_version);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, db_name, factory, db_version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
