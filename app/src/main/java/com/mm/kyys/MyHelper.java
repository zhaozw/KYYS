package com.mm.kyys;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import com.mm.kyys.Activity.ChatActivity;
import com.mm.kyys.Receiver.CallReceiver;
import com.mm.kyys.Util.AllData;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Util.SharedPreferencesManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by 27740 on 2017/1/6.
 */

public class MyHelper implements EaseUI.EaseUserProfileProvider {


    private static MyHelper instance = null;

    private CallReceiver callReceiver;
    private LocalBroadcastManager broadcastManager;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    private EaseUI easeUI;


    private MyHelper() {
    }

    public synchronized static MyHelper getInstance() {
        if (instance == null) {
            instance = new MyHelper();
        }
        return instance;
    }

    /**
     * init helper
     *
     * @param context
     *            application context
     */
    public void init(final Context context) {
        EaseUI.getInstance().init(context, null);
        //use default options if options is null
        EMClient.getInstance().setDebugMode(true);


        broadcastManager = LocalBroadcastManager.getInstance(context);
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        if(callReceiver == null){
            callReceiver = new CallReceiver();
        }

        //setNotification(context);
        //注册视频语音广播
        context.registerReceiver(callReceiver, callFilter);
        //建立环信监听
        BuildListener(context);
        //将RAW下的资源文件复制到手机存储空间
        MyUtil.getIntance().copyFromRaw(context,R.raw.kyys_video,"video_music.mp3");
        //设置用户头像和昵称
        UserNickPhotoProvider();

    }


    private void BuildListener(final Context context){
        EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, CallError error) {
                switch (callState) {
                    case CONNECTING: // 正在连接对方
                        //Toast.makeText(context,"正在连接对方",Toast.LENGTH_LONG).show();
                        //broadcastManager.sendBroadcast(new Intent(MainActivity.VIDEO_REQUEST));
                        break;
                    case CONNECTED: // 双方已经建立连接
                        //Toast.makeText(context,"双方已经建立连接",Toast.LENGTH_LONG).show();
                        broadcastManager.sendBroadcast(new Intent(AllData.HAD_CONNECTIONED));
                        break;
                    case ACCEPTED: // 电话接通成功
                        //Toast.makeText(context,"电话接通成功",Toast.LENGTH_LONG).show();
                        broadcastManager.sendBroadcast(new Intent(AllData.CONNECTION_SUEESSFUL));
                        break;
                    case DISCONNECTED: // 电话断了
                        //Toast.makeText(context,"电话断了",Toast.LENGTH_LONG).show();
                        broadcastManager.sendBroadcast(new Intent(AllData.CONNECTION_SHUTDOWN));
                        break;
                    case NETWORK_UNSTABLE: //网络不稳定
                        if(error == CallError.ERROR_NO_DATA){
                            //无通话数据
                            Toast.makeText(context,"网络不稳定 无通话数据",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context,"网络不稳定",Toast.LENGTH_LONG).show();
                        }
                        broadcastManager.sendBroadcast(new Intent(AllData.CONNECTION_SHUTDOWN));
                        break;
                    case NETWORK_NORMAL: //网络恢复正常
                        //Toast.makeText(context,"网络恢复正常",Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        });
        EMMessageListener msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                String activityName = MyUtil.getIntance().getRunningActivityName(context);
                Log.e("xl", "当前运行activity名称："+activityName);
                //收到消息
                Log.e("xl", "收到消息");
                if (hasUnreadMessage()){
                    broadcastManager.sendBroadcast(new Intent(AllData.RECEIVE_MESSAGE));
                }
                if(!activityName.equals("com.mm.kyys.Activity.ChatActivity")){
                    Log.e("xl", "通知栏");
                    for (EMMessage message : messages) {
                        //EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
                        // in background, do not refresh UI, notify it in notification bar

                        try {
                            Log.e("xl", "messsage username = "+message.getUserName());
                            Log.e("xl", "messsage nick = "+message.getStringAttribute("nick").toString());
                            Log.e("xl", "messsage pic = "+message.getStringAttribute("pic").toString());
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                        easeUI = EaseUI.getInstance();
                        if(!easeUI.hasForegroundActivies()){
                            getNotifier().onNewMsg(message);
                        }
                    }
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> list) {
                //已读消息
            }

            @Override
            public void onMessageDelivered(List<EMMessage> list) {
                //已送达消息
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);

    }



    public boolean isLoggedIn() {
        return EMClient.getInstance().isLoggedInBefore();
    }

    @Override
    public EaseUser getUser(String username) {
        EaseUser user = new EaseUser(username);
        user.setNick("xl");

        return user;
    }

    public boolean hasUnreadMessage(){
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        for (EMConversation ec:conversations.values()){
            if (ec.getUnreadMsgCount()>0){
                AllData.HAS_UNREAD_MESSAGE = true;
                break;
            }else{
                AllData.HAS_UNREAD_MESSAGE = false;
            }
        }
        return AllData.HAS_UNREAD_MESSAGE;
    }

    public EaseNotifier getNotifier(){
        return easeUI.getNotifier();
    }

    public void UserNickPhotoProvider(){
        EaseUI easeui = EaseUI.getInstance();
        easeui.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return getUserInfo(username);
            }
        });
    }

    public EaseUser getUserInfo(String username){
        EaseUser user = null;
        user = new EaseUser(username);
        Log.e("xl", "查询"+username+"的信息");
        if (username.equals("l")){
            user.setNickname("段志宇");
            user.setAvatar("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3018777119,2532860069&fm=21&gp=0.jpg");
        }else if(username.equals("x")){
            user.setNickname("X");
            user.setAvatar("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3230112726,3777160653&fm=116&gp=0.jpg");
        }
/*        user.setNickname("x");
        user.setAvatar("http://img2.imgtn.bdimg.com/it/u=3630772122,2381812656&fm=23&gp=0.jpg");*/
        return user;
    }

}
