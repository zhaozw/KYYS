package com.mm.kyys.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMCallManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.media.EMLocalSurfaceView;
import com.hyphenate.media.EMOppositeSurfaceView;
import com.mm.kyys.Model.HxUserInfo;
import com.mm.kyys.R;
import com.mm.kyys.Util.AllData;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Util.SharedPreferencesManager;
import com.mm.kyys.Wighet.XlTitle;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;

/**
 * Created by 27740 on 2017/1/11.
 */

public class VideoOrVoiceActivity extends Activity implements View.OnClickListener {


    private Activity oThis;
    private XlTitle title;



    private LinearLayout layout_connectioned;
    private EMLocalSurfaceView sf_local;
    private EMOppositeSurfaceView sf_oppo;
    private Button btn_hungup, btn_speakerphone,btn_changecamera;

    private LinearLayout layout_request;
    private TextView tv_request_info;
    private Button btn_hungup_request;
    private ImageView iv_userphoto_request;

    private LinearLayout layout_response;
    private TextView tv_response_info;
    private Button btn_hungup_response,btn_ok_response;
    private ImageView iv_userphoto_response;

    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;

    private boolean speakerphoneon = false;
    private boolean had_connected = false;
    private String state = "";
    private String type = "";
    private String touserid = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoorvoice);
        init();
        inevent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        state = "";
        touserid = "";
        unregisterBroadcastReceiver();


    }

    @Override
    public void onClick(View v) {

            switch (v.getId()) {
                //已经建立连接
                case R.id.videoorvoice_btn_shutdown:
                    try {
                        EMClient.getInstance().callManager().endCall();
                    } catch (EMNoActiveCallException e) {
                        e.printStackTrace();
                    }
                    oThis.finish();
                    break;
                case R.id.videoorvoice_btn_speakerphone:
                    speakerphoneon = !speakerphoneon;
                    MyUtil.getIntance().setSpeakerphoneOn(speakerphoneon,oThis);
                    if (speakerphoneon) {
                        btn_speakerphone.setText(R.string.guanbimianti);
                    } else {
                        btn_speakerphone.setText(R.string.kaiqimianti);
                    }
                    break;
                case R.id.videoorvoice_btn_changecamera:
                    EMClient.getInstance().callManager().switchCamera();
                    break;
                //请求连接
                case R.id.videoorvoice_btn_hungup_request:
                    MyUtil.getIntance().stopVideoRing();
                    try {
                        EMClient.getInstance().callManager().rejectCall();
                    } catch (EMNoActiveCallException e) {
                        e.printStackTrace();
                    }
                    oThis.finish();
                    break;
                //响应连接请求
                case R.id.videoorvoice_btn_ok_response:
                    if (had_connected){
                        MyUtil.getIntance().stopVideoRing();
                        try {
                            EMClient.getInstance().callManager().answerCall();
                        } catch (EMNoActiveCallException e) {
                            e.printStackTrace();
                        }
                        state = "connectioned";
                        chooseState();
                    }
                    break;
                case R.id.videoorvoice_btn_hungup_response:
                    MyUtil.getIntance().stopVideoRing();
                    try {
                        EMClient.getInstance().callManager().rejectCall();
                    } catch (EMNoActiveCallException e) {
                        e.printStackTrace();
                    }
                    oThis.finish();//https://code.csdn.net/qq_34114175/xl_kyys.git

                    break;
            }
    }

    private void init() {

        oThis = this;

        registerBroadcastReceiver();

        title = (XlTitle) findViewById(R.id.videoorvoice_title);

        //已经建立连接
        layout_connectioned = (LinearLayout) findViewById(R.id.videoorvoice_layout_connectioned);
        sf_local = (EMLocalSurfaceView) findViewById(R.id.videoorvoice_sf_small);
        sf_oppo = (EMOppositeSurfaceView) findViewById(R.id.videoorvoice_sf_big);
        btn_hungup = (Button) findViewById(R.id.videoorvoice_btn_shutdown);
        btn_speakerphone = (Button) findViewById(R.id.videoorvoice_btn_speakerphone);
        btn_changecamera = (Button) findViewById(R.id.videoorvoice_btn_changecamera);
        //请求建立连接
        layout_request = (LinearLayout) findViewById(R.id.videoorvoice_layout_request);
        tv_request_info = (TextView) findViewById(R.id.videoorvoice_tv_info_request);
        btn_hungup_request = (Button) findViewById(R.id.videoorvoice_btn_hungup_request);
        iv_userphoto_request = (ImageView) findViewById(R.id.videoorvoice_iv_userphoto_request);
        //响应连接请求
        layout_response = (LinearLayout) findViewById(R.id.videoorvoice_layout_response);
        tv_response_info = (TextView) findViewById(R.id.videoorvoice_tv_info_response);
        btn_ok_response = (Button) findViewById(R.id.videoorvoice_btn_ok_response);
        btn_hungup_response = (Button) findViewById(R.id.videoorvoice_btn_hungup_response);
        iv_userphoto_response = (ImageView) findViewById(R.id.videoorvoice_iv_userphoto_response);

        btn_hungup.setOnClickListener(this);
        btn_speakerphone.setOnClickListener(this);
        btn_changecamera.setOnClickListener(this);

        btn_hungup_request.setOnClickListener(this);

        btn_ok_response.setOnClickListener(this);
        btn_hungup_response.setOnClickListener(this);


    }

    private void inevent() {
        state = getIntent().getStringExtra("state");
        type = getIntent().getStringExtra("type");
        touserid = getIntent().getStringExtra("toUserid");

        title.setLeftVisibility(false);
        title.setRightVisibility(false);

        sf_local.setZOrderMediaOverlay(true);
        sf_local.setZOrderOnTop(true);

        float[] arr = MyUtil.getIntance().screenPix(oThis);
        int w = (int)arr[0];
        int h = (int)arr[1];
        Log.e("xl", h+"----"+w);

        if (type.equals("video")) {
            //设置通话最大帧率，SDK 最大支持(30)，默认(20)
            EMClient.getInstance().callManager().getCallOptions().setMaxVideoFrameRate(25);
            //设置视频通话分辨率 默认是(640, 480)

            //EMClient.getInstance().callManager().getCallOptions().setVideoResolution(h,w);
            //设置视频通话最大和最小比特率（可以不设置，SDK会根据手机分辨率和网络情况自动适配），最大值默认800， 最小值默认80
            EMClient.getInstance().callManager().getCallOptions().setMaxVideoKbps(800);
            EMClient.getInstance().callManager().getCallOptions().setMinVideoKbps(80);

            //EMClient.getInstance().callManager().getCallOptions().setVideoResolution(200,100);
            //EMClient.getInstance().callManager().getCallOptions().setMaxVideoFrameRate(25);
            EMClient.getInstance().callManager().setSurfaceView(sf_local, sf_oppo);
        }else if(type.equals("voice")){
            //音视频呼叫对方，如果对方不在线，则发送一条离线消息通知对方（true推送，false不推送）
            EMClient.getInstance().callManager().getCallOptions().setIsSendPushIfOffline(false);
        }

        MyUtil.getIntance().initMediaPlayer();
        chooseState();
    }

    private void chooseState(){
        switch (state){
            case "connectioned":
                Log.e("xl", "connectioned");
                layout_request.setVisibility(View.GONE);
                layout_response.setVisibility(View.GONE);
                layout_connectioned.setVisibility(View.VISIBLE);
                if (type.equals("video")){
                    title.setText(getResources().getString(R.string.shipintonghuazhong));
                    btn_changecamera.setVisibility(View.VISIBLE);
                    sf_local.setVisibility(View.VISIBLE);
                    sf_oppo.setVisibility(View.VISIBLE);
                }else{
                    title.setText(getResources().getString(R.string.yuyintonghuazhong));
                    btn_changecamera.setVisibility(View.GONE);
                    sf_local.setVisibility(View.GONE);
                    sf_oppo.setVisibility(View.GONE);
                    MyUtil.getIntance().setSpeakerphoneOn(false,oThis);
                }

                break;
            case "request":
                Log.e("xl", "request");
                MyUtil.getIntance().playVideoRing(oThis);
                layout_request.setVisibility(View.VISIBLE);
                layout_response.setVisibility(View.GONE);
                layout_connectioned.setVisibility(View.GONE);
                sf_local.setVisibility(View.GONE);
                tv_request_info.setText("");
                if (type.equals("video")){
                    title.setText(getResources().getString(R.string.lianjiezhong));
                }else{
                    title.setText(getResources().getString(R.string.lianjiezhong));
                }
                iv_userphoto_request.setVisibility(View.VISIBLE);
                Log.e("xl", "视频用户hx_id"+touserid);
                HxUserInfo userInfo_request = SharedPreferencesManager.getIntance(oThis).getUserNickPicHxID(touserid,oThis);
                ImageLoader.getInstance().displayImage(userInfo_request.getHx_img(),iv_userphoto_request);

                break;
            case "response":
                Log.e("xl", "response");
                MyUtil.getIntance().playVideoRing(oThis);
                layout_request.setVisibility(View.GONE);
                layout_response.setVisibility(View.VISIBLE);
                layout_connectioned.setVisibility(View.GONE);
                tv_request_info.setText("");
                if (type.equals("video")){
                    title.setText(getResources().getString(R.string.qqjxspth));
                }else{
                    title.setText(getResources().getString(R.string.qqjxyyth));
                }
                iv_userphoto_response.setVisibility(View.VISIBLE);
                Log.e("xl", "视频用户hx_id"+touserid);
                HxUserInfo userInfo_response = SharedPreferencesManager.getIntance(oThis).getUserNickPicHxID(touserid,oThis);
                ImageLoader.getInstance().displayImage(userInfo_response.getHx_img(),iv_userphoto_response);

                break;
        }
    }


    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(oThis);
        IntentFilter filter = new IntentFilter();
        filter.addAction(AllData.VIDEO_REQUEST);
        filter.addAction(AllData.VOICE_REQUEST);
        filter.addAction(AllData.CONNECTION_SUEESSFUL);
        filter.addAction(AllData.CONNECTION_SHUTDOWN);
        filter.addAction(AllData.HAD_CONNECTIONED);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action) {
                    case AllData.VIDEO_REQUEST:
                        Toast.makeText(context, "进行收到视频请求的UI操作", Toast.LENGTH_LONG).show();
                        break;
                    case AllData.VOICE_REQUEST:
                        Toast.makeText(context, "进行收到语音请求的UI操作", Toast.LENGTH_LONG).show();
                        break;
                    case AllData.I_AGREE:
                        break;
                    case AllData.OPPO_AGREE:
                        break;
                    case AllData.HAD_CONNECTIONED:
                        had_connected = true;
                        break;
                    case AllData.CONNECTION_SUEESSFUL:
                        MyUtil.getIntance().stopVideoRing();
                        state = "connectioned";
                        chooseState();
                        break;
                    case AllData.CONNECTION_SHUTDOWN:
                        MyUtil.getIntance().stopVideoRing();
                        had_connected = false;
                        oThis.finish();
                        break;
                }
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, filter);
    }

    private void unregisterBroadcastReceiver() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

}