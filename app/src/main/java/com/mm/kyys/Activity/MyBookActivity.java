package com.mm.kyys.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mm.kyys.R;
import com.mm.kyys.Util.RestClient;
import com.mm.kyys.Util.SharedPreferencesManager;
import com.mm.kyys.Wighet.XlTitle;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 27740 on 2017/2/22.
 */

public class MyBookActivity extends BaseActivity {

    private Activity oThis;
    private XlTitle title;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_mybook);
        init();
        inevent();
    }

    private void init(){

        oThis = this;

        title = (XlTitle) findViewById(R.id.mybook_title);
    }

    private void inevent(){

        title.setText(getResources().getString(R.string.wodeyuyue));
        getBookInfo();
    }


    private void getBookInfo(){
        //id=123&type=0
        RequestParams params = new RequestParams();
        params.add("id", SharedPreferencesManager.getIntance(oThis).getUserInfo(oThis).getUserID());
        params.add("type",SharedPreferencesManager.getIntance(oThis).getUserInfo(oThis).getType()+"");
        RestClient.post("sel_BookList.ashx",params,new JsonHttpResponseHandler(){

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("xl", "预约列表获取失败！");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("xl", "预约列表获取成功！"+response.toString());
            }
        });


    }
}
