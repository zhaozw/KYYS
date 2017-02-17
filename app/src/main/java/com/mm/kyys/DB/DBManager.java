package com.mm.kyys.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mm.kyys.Model.Section;
import com.mm.kyys.Model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sexyXu on 2016/11/1.
 */

public class DBManager {

    private static DBManager manager = new DBManager();
    private DBopenHelper helper;
    private SQLiteDatabase db_question;


    public DBManager() {
        //helper = new DBopenHelper();
    }

    public static synchronized DBManager getInstance() {
        if (manager == null) {
            manager = new DBManager();
        }
        return manager;
    }



    public SQLiteDatabase getQuestionDB(){
        return db_question;
    }

    //保存用户
    synchronized public void SaveUser(List<User> list_user,SQLiteDatabase database_writable){
        if (database_writable.isOpen()){
            database_writable.delete(UserDao.USER_TABLE_NAME,null,null);
            for (User user : list_user){
                ContentValues values = new ContentValues();
                values.put(UserDao.USER_NAME,user.getUserName());

            }
        }
    }

    //保存科室分类
    synchronized public void SaveSection_list(List<Section> list_section, SQLiteDatabase database_writable) {
        Log.i("xl", "保存分类");
        if (database_writable.isOpen()) {
            Log.i("xl", "数据库已经打开了");
            database_writable.delete(SectionDao.SECTION_TABLE_NAME, null, null);
            for (Section section : list_section) {
                ContentValues values = new ContentValues();
                values.put(SectionDao.SECTION_DID, section.getDid());
                values.put(SectionDao.SECTION_HID, section.getHid());
                values.put(SectionDao.SECTION_NAME,section.getName());
                values.put(SectionDao.SECTION_LASTUPDATETIME,section.getLastUpdateTime());
                values.put(SectionDao.SECTION_FLAG,section.getFlag());
                values.put(SectionDao.SECTION_TYPE,section.getType());
                values.put(SectionDao.SECTION_DID2,section.getDid2());
                values.put(SectionDao.SECTION_IMG,section.getImg());
                database_writable.replace(SectionDao.SECTION_TABLE_NAME, null, values);
            }
        }
    }

    synchronized public void DeleteSection(String section_name, SQLiteDatabase database_writable,SQLiteDatabase database_readable) {
        if (database_writable.isOpen()) {
            database_writable.delete(SectionDao.SECTION_TABLE_NAME,SectionDao.SECTION_NAME + " = ?", new String[]{section_name});
        }
    }


    //查询可不可以不同步？  synchronized
    public List<Section> QuerySection(int type,String did2, SQLiteDatabase database_readable) {
        List<Section> list = new ArrayList<Section>();
        //SQLiteDatabase db = helper.getReadableDatabase();
        // 调用SQLiteDatabase对象的query方法进行查询，返回一个Cursor对象：由数据库查询返回的结果集对象
        // 第一个参数String：表名
        // 第二个参数String[]:要查询的列名
        // 第三个参数String：查询条件
        // 第四个参数String[]：查询条件的参数
        // 第五个参数String:对查询的结果进行分组
        // 第六个参数String：对分组的结果进行限制
        // 第七个参数String：对查询的结果进行排序
        /*Cursor cursor = database_readable.query(SectionDao.SECTION_TABLE_NAME, new String[] { SectionDao.SECTION_NAME,
                "name" }, "id=?", new String[] { "1" }, null, null, null);*/

        String[] arr = {did2,"2"};
        Cursor cursor;
        //cursor = database_readable.query(SectionDao.SECTION_TABLE_NAME, null, SectionDao.SECTION_DID2 + "=?", arr, null, null, null);

        switch (type){
            case 0:
                cursor = database_readable.query(SectionDao.SECTION_TABLE_NAME, null,null,null, null, null, null);
                break;
            case 1:
                cursor = database_readable.query(SectionDao.SECTION_TABLE_NAME, null,SectionDao.SECTION_TYPE+"=?",new String[]{"1"}, null, null, null);
                break;
            case 2:
                cursor = database_readable.query(SectionDao.SECTION_TABLE_NAME, null, SectionDao.SECTION_DID2 + "=? and "+SectionDao.SECTION_TYPE+"=?", arr,null, null, null);
                break;
            default:
                cursor = database_readable.query(SectionDao.SECTION_TABLE_NAME, null,null,null, null, null, null);
                break;
        }

        while (cursor.moveToNext()) {

            String section_did = cursor.getString(cursor.getColumnIndex(SectionDao.SECTION_DID));
            String section_hid = cursor.getString(cursor.getColumnIndex(SectionDao.SECTION_HID));
            String section_name = cursor.getString(cursor.getColumnIndex(SectionDao.SECTION_NAME));
            String section_lastupdatetime = cursor.getString(cursor.getColumnIndex(SectionDao.SECTION_LASTUPDATETIME));
            int section_flag = cursor.getInt(cursor.getColumnIndex(SectionDao.SECTION_FLAG));
            int section_type = cursor.getInt(cursor.getColumnIndex(SectionDao.SECTION_TYPE));
            String section_did2 = cursor.getString(cursor.getColumnIndex(SectionDao.SECTION_DID2));
            String section_img = cursor.getString(cursor.getColumnIndex(SectionDao.SECTION_IMG));

            list.add(new Section(section_did, section_hid, section_name, section_lastupdatetime, section_flag, section_type, section_did2, section_img));
        }
        return list;
    }

    synchronized public void closeDB() {
        if (helper != null) {
            helper.closeDB();
        }
        helper = null;
    }
}
