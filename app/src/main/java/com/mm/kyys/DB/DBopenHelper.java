package com.mm.kyys.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by sexyXu on 2016/11/1.
 */

public class DBopenHelper extends SQLiteOpenHelper {

    private static String TAG = "数据库HELPER";

    //数据库版本
    public static final int DB_VERSION = 1;

    public static DBopenHelper instance;

    public static String CLASSIFY_TABLE_CREAT = "create table "
            + ClassifyDao.CLASSIFY_TABLE_NAME +"("
            + ClassifyDao.CLASSIFY_CATEID +" integer,"
            + ClassifyDao.CLASSIFY_DISPLAYORDER +" integer,"
            + ClassifyDao.CLASSIFY_NAME + " varchar(255),"
            + ClassifyDao.CLASSIFY_PRICERANGE + " varchar(255),"
            + ClassifyDao.CLASSIFY_PARENTID + " integer,"
            + ClassifyDao.CLASSIFY_LAYER + " integer,"
            + ClassifyDao.CLASSIFY_HASCHILD + " integer,"
            + ClassifyDao.CLASSIFY_PATH+ " varchar(255));";




    public DBopenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBopenHelper getIntance(Context context){
        if (instance==null){
            instance = new DBopenHelper(context.getApplicationContext(),"kyys.db",null,1);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "建表");
        db.execSQL(CLASSIFY_TABLE_CREAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "#############数据库升级了##############:" + DB_VERSION+ "OLD VERSION:"+ oldVersion + " NEW VERSION:"+ newVersion);
    }

    public void closeDB(){
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }
}
