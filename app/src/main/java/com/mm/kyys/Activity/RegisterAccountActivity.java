package com.mm.kyys.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mm.kyys.R;
import com.mm.kyys.Util.AllData;
import com.mm.kyys.Util.RestClient;
import com.mm.kyys.Wighet.XlTitle;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;

/**
 * Created by 27740 on 2017/2/14.
 */

public class RegisterAccountActivity extends BaseActivity {

    private XlTitle title;
    private EditText et_account,et_pwd,et_pwd_again,et_identifying;
    private Button btn_send_identifying,btn_regist;
    private Activity oThis;

    private String account,password;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_registeraccount);

        init();
        inevent();
    }

    public void init(){

        oThis = this;

        title = (XlTitle) findViewById(R.id.registeraccount_title);
        et_account = (EditText) findViewById(R.id.registeraccount_et_account);
        et_pwd = (EditText) findViewById(R.id.registeraccount_et_pwd);
        et_pwd_again = (EditText) findViewById(R.id.registeraccount_et_pwd_again);
        et_identifying = (EditText) findViewById(R.id.registeraccount_et_identifying);
        btn_regist = (Button) findViewById(R.id.registeraccount_btn_submit);
        btn_send_identifying = (Button) findViewById(R.id.registeraccount_btn_sendidentifying);

    }

    private void inevent(){

        title.setleftText(R.string.fanhui);
        title.setText(getResources().getString(R.string.zhuce));
        title.setLeftVisibility(true);
        title.setleftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oThis.finish();
                oThis.overridePendingTransition(R.anim.fragment_slide_in_from_left,R.anim.fragment_slide_out_from_right);
            }
        });

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_account.getText())||TextUtils.isEmpty(et_pwd.getText())||TextUtils.isEmpty(et_pwd_again.getText())||TextUtils.isEmpty(et_identifying.getText())){
                    Toast.makeText(oThis,getResources().getString(R.string.zcxxbwz),Toast.LENGTH_LONG).show();
                } else if (!et_pwd.getText().toString().equals(et_pwd_again.getText().toString())){
                    Toast.makeText(oThis,getResources().getString(R.string.mmsrnyz),Toast.LENGTH_LONG).show();
                } else if (et_pwd.getText().toString().length()<6||et_pwd.getText().toString().length()>16){
                    Toast.makeText(oThis,getResources().getString(R.string.nsrdmmbfhyq),Toast.LENGTH_LONG).show();
                }else {
                    account = et_account.getText().toString();
                    password = et_pwd.getText().toString();
                    ToRegister();
                }
            }
        });
    }

    private void ToRegister(){
        //http://101.201.31.32/api/in_User.ashx?account=312132&password=1132132
        Log.e("xl", "ToRegister:"+account+"--"+password);
        RequestParams params = new RequestParams();
        params.add("account",account);
        params.add("password",password);
        RestClient.get("in_User.ashx",params,new JsonHttpResponseHandler(){

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("xl", "注册失败");

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("xl", "注册信息："+response);

            }
        });

    }
}
