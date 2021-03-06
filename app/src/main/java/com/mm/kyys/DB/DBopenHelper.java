package com.mm.kyys.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mm.kyys.Model.Section;

/**
 * Created by sexyXu on 2016/11/1.
 */

public class DBopenHelper extends SQLiteOpenHelper {

    private static String TAG = "数据库HELPER";

    //数据库版本
    public static final int DB_VERSION = 1;

    public static DBopenHelper instance;

    public static String SECTION_TABLE_CREAT = "create table "
            + SectionDao.SECTION_TABLE_NAME +"("
            + SectionDao.SECTION_DID +" varchar(255),"
            + SectionDao.SECTION_HID +" varchar(255),"
            + SectionDao.SECTION_NAME + " varchar(255),"
            + SectionDao.SECTION_LASTUPDATETIME + " varchar(255),"
            + SectionDao.SECTION_FLAG + " integer,"
            + SectionDao.SECTION_TYPE + " integer,"
            + SectionDao.SECTION_DID2 + " varchar(255),"
            + SectionDao.SECTION_IMG + " varchar(255));";

    public static String USER_TABLE_CREAT = "create table "
            + UserDao.USER_TABLE_NAME + "("
            + UserDao.USER_NAME + " varchar(255),"
            + UserDao.USER_AGE + " varchar(255),"
            + UserDao.USER_NICK + " varchar(255),"
            + UserDao.USER_PHOTO + " varchar(255),"
            + UserDao.USER_SEX + " varchar(255));";


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
        Log.e("xl", "建表——Section："+SECTION_TABLE_CREAT);
        Log.e("xl", "建表——User："+USER_TABLE_CREAT);
        db.execSQL(SECTION_TABLE_CREAT);
        db.execSQL(USER_TABLE_CREAT);
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
