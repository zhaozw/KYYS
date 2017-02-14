package com.mm.kyys.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mimei.sq.Model.Node;
import com.mimei.sq.Model.Question;
import com.mimei.sq.R;

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

    /**
     * 将raw文件夹下的数据库导入到手机存储空间
     *
     * @param context  上下文
     * @param db_path   导入到手机的存储路径  .../myDBfile/
     * @return  数据库对象
     * @author xl
     */
    public SQLiteDatabase copyDBFrom(Context context, String db_path) {

        try {
            InputStream is = context.getResources().openRawResource(R.raw.exam); // 欲导入的数据库
            //如果目标数据库在手机上不存在则导入
            if (!(new File(db_path).exists())) {

                FileOutputStream fos;
                fos = new FileOutputStream(db_path);
                byte[] buffer = new byte[40000];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (FileNotFoundException e) {
            Log.i("xl", "FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("xl", "IOException");
            e.printStackTrace();
        }
        db_question = context.openOrCreateDatabase(db_path,
                Context.MODE_PRIVATE, null);
        return db_question;
    }

    public SQLiteDatabase getQuestionDB(){
        return db_question;
    }


    synchronized public List<Question> QueryQuestion(SQLiteDatabase db, String table_name, String sql_where, String[] param){

        List<Question> list_question = new ArrayList<Question>();

        int count = 0;
        Cursor cursor;
        cursor = db.query(table_name, null,sql_where, param, null, null, null);

        while (cursor.moveToNext()) {
            count++;
            Question question = new Question();
            question.setID(cursor.getString(cursor.getColumnIndex("ID")));
            question.setZHANGJIE(cursor.getString(cursor.getColumnIndex("ZHANGJIE")));
            question.setQUESTION(cursor.getString(cursor.getColumnIndex("QUESTION")));
            question.setANSS(cursor.getString(cursor.getColumnIndex("ANSS")));
            question.setANS(cursor.getString(cursor.getColumnIndex("ANS")));
            question.setBMP(cursor.getString(cursor.getColumnIndex("BMP")));
            question.setTXBZ(cursor.getString(cursor.getColumnIndex("TXBZ")));
            question.setYCBZ(cursor.getString(cursor.getColumnIndex("YCBZ")));
            question.setFLASH(cursor.getString(cursor.getColumnIndex("FLASH")));
            question.setSTATE(cursor.getString(cursor.getColumnIndex("STATE")));
            list_question.add(question);
        }
        return list_question;
    }


    synchronized public void SaveClassify_list(List<Node> list_node, SQLiteDatabase database_writable) {
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

    //查询可以不可以不同步  synchronized
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
        /*Cursor cursor = db.query(ClassifyDao.CLASSIFY_TABLE_NAME, new String[] { ClassifyDao.CLASSIFY_NAME,
                "name" }, "id=?", new String[] { "1" }, null, null, null);*/

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
    }

    synchronized public void closeDB() {
        if (helper != null) {
            helper.closeDB();
        }
        helper = null;
    }
}
