package com.mm.kyys.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mm.kyys.Model.Doctor;
import com.mm.kyys.Model.User;
import com.mm.kyys.R;
import com.mm.kyys.Util.AllData;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Util.RestClient;
import com.mm.kyys.Util.SharedPreferencesManager;
import com.mm.kyys.View.CircularImage;
import com.mm.kyys.View.SweetAlertDialog;
import com.mm.kyys.Wighet.XlTitle;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 27740 on 2017/1/16.
 */

public class DoctorDetialActivity extends Activity {

    private Activity oThis;
    private Button btn_gh,btn_yy;
    private CircularImage iv_photo;
    private Boolean canTakeVideo = false;
    private XlTitle title;
    private TextView tv_doctor_name,tv_doctor_identify,tv_doctor_intrduction;
    private Doctor doctor;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctordetial);
        init();
        inevent();
        Log.e("xl", "onCreate: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("xl", "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user.getType()==0){
            canTakeVideo = SharedPreferencesManager.getIntance(oThis).getRegisterInfo(user.getUserID()+"_"+doctor.getUid());
        }

        if (canTakeVideo||user.getType()==1){
            btn_gh.setText(R.string.lianxiyisheng);
        }else{
            btn_gh.setText(R.string.guahao);
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.em_icon_account) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.em_icon_account) //设置图片Uri为空或是错误的时候显示的图片 
                .showImageOnFail(R.mipmap.em_icon_account) //设置图片加载/解码过程中错误时候显示的图片
                .build();
        ImageLoader.getInstance().displayImage(doctor.getImg(),iv_photo,options);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init(){

        oThis = this;

        btn_gh = (Button) findViewById(R.id.doctordetial_btn_gh);
        btn_yy = (Button) findViewById(R.id.doctordetial_btn_yy);

        iv_photo = (CircularImage) findViewById(R.id.doctordetial_iv_photo);
        title = (XlTitle) findViewById(R.id.doctordetial_title);
        tv_doctor_name = (TextView) findViewById(R.id.doctordetial_tv_name);
        tv_doctor_identify = (TextView) findViewById(R.id.doctordetial_tv_identify);
        tv_doctor_intrduction = (TextView) findViewById(R.id.doctordetial_tv_intrduction);

    }

    private void inevent(){

        user = SharedPreferencesManager.getIntance(oThis).getUserInfo(oThis);

        Bundle bundle = getIntent().getExtras();
        doctor = bundle.getParcelable("doctor");
        tv_doctor_name.setText(doctor.getName());
        tv_doctor_identify.setText(doctor.getType());
        tv_doctor_intrduction.setText(doctor.getAdvantage());

        title.setText(getResources().getString(R.string.yishengweizhan));
        title.setleftText(R.string.fanhui);
        title.setrightText(R.string.shouye);
        title.setleftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oThis.finish();
                oThis.overridePendingTransition(R.anim.fragment_slide_in_from_left,R.anim.fragment_slide_out_from_right);
            }
        });
        title.setrightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(oThis,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ImageLoader.getInstance().displayImage(doctor.getImg(),iv_photo);

        btn_gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canTakeVideo||user.getType()==1){
                    btn_gh.setText(R.string.lianxiyisheng);

                    String tousername = doctor.getUid();

                    Intent intent = new Intent(oThis, ChatActivity.class);
                    intent.putExtra(EaseConstant.EXTRA_USER_ID,tousername);
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                    startActivity(intent);
                    oThis.overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_from_left);
                }else{
                    Log.e("xl", "123:f");
                    btn_gh.setText(R.string.guahao);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("doctor",doctor);
                    MyUtil.getIntance().ToActivity(oThis,PayActivity.class,true,bundle);
                    MyUtil.getIntance().ActivityStartAmni(oThis);

                }

            }
        });

        btn_yy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toBook();
            }
        });

    }

    private void toBook(){
        //dcid=123&userID=123
        RequestParams params = new RequestParams();
        params.add("dcid",doctor.getDcid());
        params.add("userID",SharedPreferencesManager.getIntance(oThis).getUserInfo(oThis).getUserID());
        RestClient.post("in_Book.ashx",params,new JsonHttpResponseHandler(){

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("xl", "预约失败信息："+errorResponse.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("xl", "预约成功信息："+response.toString());
                SweetAlertDialog dialog = new SweetAlertDialog(oThis,SweetAlertDialog.SUCCESS_TYPE);
                dialog.setContentText(getResources().getString(R.string.yuyuechenggong));
                dialog.show();
            }
        });



    }

}
