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
import com.mm.kyys.Model.Doctor;
import com.mm.kyys.R;
import com.mm.kyys.Util.AllData;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Util.SharedPreferencesManager;
import com.mm.kyys.View.CircularImage;
import com.mm.kyys.Wighet.XlTitle;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by 27740 on 2017/1/16.
 */

public class DoctorDetialActivity extends Activity {

    private Activity oThis;
    private Button btn_gh;
    private CircularImage iv_photo;
    private Boolean canTakeVideo;
    private XlTitle title;
    private TextView tv_doctor_name,tv_doctor_identify,tv_doctor_intrduction;
    private int identity;
    private Doctor doctor;

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
        identity = SharedPreferencesManager.getIntance(oThis).getUserInfo(oThis).getType();
        canTakeVideo = SharedPreferencesManager.getIntance(oThis).getRegisterInfo("userid"+"123");
        if (canTakeVideo||identity==1){
            btn_gh.setText(R.string.lianxiyisheng);
        }else{
            btn_gh.setText(R.string.guahao);
        }

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher) //设置图片Uri为空或是错误的时候显示的图片 
                .showImageOnFail(R.drawable.ic_launcher) //设置图片加载/解码过程中错误时候显示的图片
                .build();
        ImageLoader.getInstance().displayImage("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3018777119,2532860069&fm=21&gp=0.jpg",iv_photo,options);
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
        iv_photo = (CircularImage) findViewById(R.id.doctordetial_iv_photo);
        title = (XlTitle) findViewById(R.id.doctordetial_title);
        tv_doctor_name = (TextView) findViewById(R.id.doctordetial_tv_name);
        tv_doctor_identify = (TextView) findViewById(R.id.doctordetial_tv_identify);
        tv_doctor_intrduction = (TextView) findViewById(R.id.doctordetial_tv_intrduction);

    }

    private void inevent(){

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

        ImageLoader.getInstance().displayImage("http://img2.imgtn.bdimg.com/it/u=3136611519,4107260583&fm=23&gp=0.jpg",iv_photo);

        btn_gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canTakeVideo||identity==1){
                    btn_gh.setText(R.string.lianxiyisheng);

                    String tousername = doctor.getUid();

                    Intent intent = new Intent(oThis, ChatActivity.class);
                    intent.putExtra(EaseConstant.EXTRA_USER_ID,tousername);
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                    //intent.putExtra(EaseConstant.EXTRA_USER_NICK,touserNick);
                    startActivity(intent);
                    oThis.overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_from_left);
                }else{
                    Log.e("xl", "123:f");
                    btn_gh.setText(R.string.guahao);
                    MyUtil.getIntance().ToActivity(oThis,PayActivity.class,true,null);
                    MyUtil.getIntance().ActivityStartAmni(oThis);
                    Bundle bundle = new Bundle();

                    //MyUtil.getIntance().ToActivity(oThis,MyOrderActivity.class,true,null);

                }

            }
        });
    }
}
