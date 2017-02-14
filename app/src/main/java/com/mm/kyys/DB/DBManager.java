package com.mm.kyys.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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


    synchronized public void SaveUser(List<User> list_user,SQLiteDatabase database_writable){
        if (database_writable.isOpen()){
            database_writable.delete(UserDao.USER_TABLE_NAME,null,null);
            for (User user : list_user){
                ContentValues values = new ContentValues();
                values.put(UserDao.USER_NAME,user.getName());

            }
        }
    }

    /*synchronized public void SaveClassify_list(List<Node> list_node, SQLiteDatabase database_writable) {
        Log.i("xl", "保存分类");
        if (database_writable.isOpen()) {
            Log.i("xl", "数据库已经打开了");
            database_writable.delete(ClassifyDao.CLASSIFY_TABLE_NAME, null, null);
            for (Node node : list_node) {
                ContentValues values = new ContentValues();
                values.put(ClassifyDao.CLASSIFY_CATEID, node.getCateid());
                values.put(ClassifyDao.CLASSIFY_DISPLAYORDER, node.getDisplayorder());
                if (node.getName() != null)
                    values.put(ClassifyDao.CLASSIFY_NAME, node.getName());
                if (node.getPricerange() != null)
                    values.put(ClassifyDao.CLASSIFY_PRICERANGE, node.getPricerange());
                values.put(ClassifyDao.CLASSIFY_PARENTID, node.getParentid());
                values.put(ClassifyDao.CLASSIFY_LAYER, node.getLayer());
                values.put(ClassifyDao.CLASSIFY_HASCHILD, node.getHaschild());
                if (node.getPath() != null)
                    values.put(ClassifyDao.CLASSIFY_PATH, node.getPath());
                database_writable.replace(ClassifyDao.CLASSIFY_TABLE_NAME, null, values);
            }
        }
    }

    synchronized public void DeleteClassify(String classify_name, SQLiteDatabase database_writable) {
        if (database_writable.isOpen()) {
            database_writable.delete(ClassifyDao.CLASSIFY_TABLE_NAME, ClassifyDao.CLASSIFY_NAME + " = ?", new String[]{classify_name});
        }
    }

    //查询可不可以不同步？  synchronized
    public List<Node> QuerySonClassify(String parentid, SQLiteDatabase database_readable) {
        List<Node> list = new ArrayList<Node>();
        //SQLiteDatabase db = helper.getReadableDatabase();
        // 调用SQLiteDatabase对象的query方法进行查询，返回一个Cursor对象：由数据库查询返回的结果集对象
        // 第一个参数String：表名
        // 第二个参数String[]:要查询的列名
        // 第三个参数String：查询条件
        // 第四个参数String[]：查询条件的参数
        // 第五个参数String:对查询的结果进行分组
        // 第六个参数String：对分组的结果进行限制
        // 第七个参数String：对查询的结果进行排序
        *//*Cursor cursor = db.query(ClassifyDao.CLASSIFY_TABLE_NAME, new String[] { ClassifyDao.CLASSIFY_NAME,
                "name" }, "id=?", new String[] { "1" }, null, null, null);*//*

        String[] arr = {parentid};
        Cursor cursor;
        cursor = database_readable.query(ClassifyDao.CLASSIFY_TABLE_NAME, null, ClassifyDao.CLASSIFY_PARENTID + "=?", arr, null, null, null);

        while (cursor.moveToNext()) {

            int c_cateid = cursor.getInt(cursor.getColumnIndex(ClassifyDao.CLASSIFY_CATEID));
            int c_displayorder = cursor.getInt(cursor.getColumnIndex(ClassifyDao.CLASSIFY_DISPLAYORDER));
            String c_name = cursor.getString(cursor.getColumnIndex(ClassifyDao.CLASSIFY_NAME));
            String c_pricerange = cursor.getString(cursor.getColumnIndex(ClassifyDao.CLASSIFY_PRICERANGE));
            int c_parentid = cursor.getInt(cursor.getColumnIndex(ClassifyDao.CLASSIFY_PARENTID));
            int c_layer = cursor.getInt(cursor.getColumnIndex(ClassifyDao.CLASSIFY_LAYER));
            int c_haschild = cursor.getInt(cursor.getColumnIndex(ClassifyDao.CLASSIFY_HASCHILD));
            String c_path = cursor.getString(cursor.getColumnIndex(ClassifyDao.CLASSIFY_PATH));

            list.add(new Node(c_cateid, c_displayorder, c_name, c_pricerange, c_parentid, c_layer, c_haschild, c_path));
        }
        return list;
    }*/

    synchronized public void closeDB() {
        if (helper != null) {
            helper.closeDB();
        }
        helper = null;
    }
}
