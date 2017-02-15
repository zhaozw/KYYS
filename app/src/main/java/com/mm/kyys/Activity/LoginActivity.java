package com.mm.kyys.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mm.kyys.Model.User;
import com.mm.kyys.R;
import com.mm.kyys.Util.AllData;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Util.RestClient;
import com.mm.kyys.Util.SharedPreferencesManager;
import com.mm.kyys.Wighet.XlProgressDialog;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 27740 on 2017/1/5.
 */

public class LoginActivity extends Activity {

    private String TAG = "登录页";
    private Activity oThis;
    private EditText et_username,et_pwd;
    private Button btn_login,btn_regist;
    private User user;
    private XlProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        inevent();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(pd!=null){
            pd.dismiss();
        }
    }

    private void init(){

        oThis = this;

        et_username = (EditText) findViewById(R.id.login_et_username);
        et_pwd = (EditText) findViewById(R.id.login_et_pwd);
        btn_login = (Button) findViewById(R.id.login_btn_login);
        btn_regist = (Button) findViewById(R.id.login_btn_regist);
    }


    private void inevent() {

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regist();
            }
        });



    }

    private void Login(){
        /*pd = new XlProgressDialog(oThis,true,getResources().getString(R.string.dengluzhong));
        //pd.show();
        EMClient.getInstance().login(et_username.getText().toString().trim(), et_pwd.getText().toString().trim(), new EMCallBack() {
            @Override
            public void onSuccess() {
                if (et_username.getText().toString().equals("x")){
                     user = new User("1","x","https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3230112726,3777160653&fm=116&gp=0.jpg"
                            ,2,"x",et_pwd.getText().toString());
                }else if (et_username.getText().toString().equals("l")){
                    user = new User("2","段志宇","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3018777119,2532860069&fm=21&gp=0.jpg"
                            ,1,"l",et_pwd.getText().toString());
                }else{
                    user = new User("3","z","http://img0.imgtn.bdimg.com/it/u=3418262349,4054465525&fm=23&gp=0.jpg"
                            ,0,"z",et_pwd.getText().toString());
                }
                String str_user = user.toString();
                Object json_user = JSON.toJSON(user);
                Log.e("xl", "JSON_USER========"+json_user.toString());
                Log.e("xl", "USER====="+user.toString());
                SharedPreferencesManager.getIntance(oThis).setUserInfo(oThis,json_user.toString());
                SharedPreferencesManager.getIntance(oThis).setUserHasLogin(true);
                AllData.getInstance().setUserName(et_username.getText().toString().trim());
                String name = SharedPreferencesManager.getIntance(oThis).getUserInfo(oThis).getName();
                Log.e("xl", "NAME======"+name);

                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "登录失败"+i+"--"+s);
                //Toast.makeText(oThis,"登录失败"+i+"--"+s,Toast.LENGTH_LONG).show();
                pd.dismiss();
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });*/
        MyUtil.getIntance().TimeToTimeStamp();

        String account = et_username.getText().toString().trim();
        String password = et_pwd.getText().toString().trim();
        RequestParams params = new RequestParams();
        params.add("account",account);
        params.add("password",password);
        RestClient.get("sel_User.ashx",params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("xl", "登录失败");

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("xl", "登录信息："+response);

            }
        });
    }

    private void Regist(){

        startActivity(new Intent(LoginActivity.this,RegisterAccountActivity.class));
        oThis.overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_from_left);


/*        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(et_username.getText().toString().trim(),et_pwd.getText().toString().trim());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.i(TAG, "注册失败："+e.getErrorCode()+"--"+e.getMessage());
                }
            }
        }).start();*/
    }

}
