package com.mm.kyys.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;


import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.PathUtil;
import com.mm.kyys.Activity.MainActivity;
import com.mm.kyys.Activity.RegisterActivity;
import com.mm.kyys.Activity.RegisterBySelfActivity;
import com.mm.kyys.Activity.VideoOrVoiceActivity;
import com.mm.kyys.Constant;
import com.mm.kyys.R;
import com.mm.kyys.Util.AllData;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Util.SharedPreferencesManager;
import com.mm.kyys.View.SweetAlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


public class ChatFragment extends EaseChatFragment implements EaseChatFragment.EaseChatFragmentHelper {

    // constant start from 11 to avoid conflict with constant in base class
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;


    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

    //red packet code : 红包功能使用的常量
    private static final int MESSAGE_TYPE_RECV_RED_PACKET = 5;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET = 6;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET_ACK = 7;
    private static final int MESSAGE_TYPE_RECV_RED_PACKET_ACK = 8;
    private static final int MESSAGE_TYPE_RECV_TRANSFER_PACKET = 9;
    private static final int MESSAGE_TYPE_SEND_TRANSFER_PACKET = 10;
    private static final int MESSAGE_TYPE_RECV_RANDOM = 11;
    private static final int MESSAGE_TYPE_SEND_RANDOM = 12;
    private static final int REQUEST_CODE_SEND_RED_PACKET = 16;
    private static final int ITEM_RED_PACKET = 16;
    private static final int REQUEST_CODE_SEND_TRANSFER_PACKET = 17;
    private static final int ITEM_TRANSFER_PACKET = 17;
    //end of red packet code


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setUpView() {
        setChatFragmentListener(this);
        super.setUpView();
        // set click listener
        titleBar.setBackgroundColor(getResources().getColor(R.color.colorBlack));
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (SharedPreferencesManager.getIntance(getContext()).getUserInfo(getActivity()).getIdentity()!=2){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }else{
                    new AlertDialog.Builder(getContext())
                            .setTitle("退出将无法继续问诊")
                            .setNegativeButton(getResources().getString(R.string.tongyi), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferencesManager.getIntance(getActivity()).setRegisterInfo("userid"+"123",false);
                                    if (EasyUtils.isSingleActivity(getActivity())) {
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                    onBackPressed();

                                }
                            })
                            .setPositiveButton(getResources().getString(R.string.jujue), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        });

    }

    @Override
    protected void registerExtendMenuItem() {
        //use the menu in base class
        super.registerExtendMenuItem();
        //发送视频文件
        inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
        //发送文件
        inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);
        //语音通话
        inputMenu.registerExtendMenuItem(R.string.attach_voice_call, R.drawable.em_chat_voice_call_selector, ITEM_VOICE_CALL, extendMenuItemClickListener);
        //视频通话
        inputMenu.registerExtendMenuItem(R.string.attach_video_call, R.drawable.em_chat_video_call_selector, ITEM_VIDEO_CALL, extendMenuItemClickListener);
        //红包
        //inputMenu.registerExtendMenuItem(R.string.attach_red_packet, R.drawable.em_chat_red_packet_selector, ITEM_RED_PACKET, extendMenuItemClickListener);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {

        }
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //send the video
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_FILE: //send the file
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_AT_USER:
                    if(data != null){
                        String username = data.getStringExtra("username");
                        inputAtUsername(username, false);
                    }
                    break;


                //end of red packet code
                default:
                    break;
            }
        }

    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {

    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }


    @Override
    public void onEnterToChatDetails() {

    }

    @Override
    public void onAvatarClick(String username) {

    }

    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
    }


    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
        //red packet code : 拆红包页面

        return false;
    }
    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //red packet code : 处理红包回执透传消息
        for (EMMessage message : messages) {
            EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
            String action = cmdMsgBody.action();//获取自定义action

        }
        //end of red packet code
        super.onCmdMessageReceived(messages);
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {

        String toUserName = "";
        if (AllData.getInstance().getUserName().equals("x")){
            toUserName = "l";
        }else{
            toUserName = "x";
        }
        boolean hasregister = SharedPreferencesManager.getIntance(getContext()).getRegisterInfo("userid"+"123");
        int identity = SharedPreferencesManager.getIntance(getContext()).getUserInfo(getActivity()).getIdentity();
        if (hasregister||identity!=2){
            switch (itemId) {

                case ITEM_VIDEO:
                    //Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                    //startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
                    break;
                case ITEM_FILE: //file
                    selectFileFromLocal();
                    break;
                case ITEM_VOICE_CALL:
                    startVoiceCall(toUserName);
                    break;
                case ITEM_VIDEO_CALL:
                    startVideoCall(toUserName);
                    break;
                //red packet code : 进入发红包页面
                case ITEM_RED_PACKET:
                    Toast.makeText(getContext(),"红包功能不可用",Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }else{
            final SweetAlertDialog sd = new SweetAlertDialog(getActivity());
            sd.setCancelable(true);
            //sd.setCanceledOnTouchOutside(true);
            sd.setTitleText(getResources().getString(R.string.qingguahao));
            sd.show();
            sd.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    getActivity().finish();
                }
            });
        }

        //keep exist extend menu
        return false;
    }

    /**
     * select file
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }

    /**
     * make a voice call
     */
    protected void startVoiceCall(String toUserName) {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(),"请求语音",Toast.LENGTH_LONG).show();
            try {//单参数
                EMClient.getInstance().callManager().makeVoiceCall(toUserName);
            } catch (EMServiceNotReadyException e) {
                e.printStackTrace();
            }
            Bundle bundle = new Bundle();
            bundle.putString("state","request");
            bundle.putString("type","voice");

            MyUtil.getIntance().ToActivity(getActivity(),VideoOrVoiceActivity.class,true,bundle);
        }
    }

    /**
     * make a video call
     */
    protected void startVideoCall(String toUserName) {
        if (!EMClient.getInstance().isConnected())
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(getContext(),"请求视频",Toast.LENGTH_LONG).show();
            try {//单参数
                EMClient.getInstance().callManager().makeVideoCall(toUserName);
            } catch (EMServiceNotReadyException e) {
                e.printStackTrace();
            }
            Bundle bundle = new Bundle();
            bundle.putString("state","request");
            bundle.putString("type","video");

            MyUtil.getIntance().ToActivity(getActivity(),VideoOrVoiceActivity.class,true,bundle);
        }

    }

    @Override
    protected void sendTextMessage(String content) {
        super.sendTextMessage(content);
        if(EaseAtMessageHelper.get().containsAtUsername(content)){
            sendAtMessage(content);
        }else{
            boolean hasregister = SharedPreferencesManager.getIntance(getContext()).getRegisterInfo("userid"+"123");
            int identity = SharedPreferencesManager.getIntance(getContext()).getUserInfo(getActivity()).getIdentity();
            if (hasregister||identity!=2){
                EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
                message.setAttribute("nick",user_nick);
                message.setAttribute("pic",SharedPreferencesManager.getIntance(getContext()).getUserInfo(getActivity()).getPhoto());
                sendMessage(message);
            }else{
                final SweetAlertDialog sd = new SweetAlertDialog(getActivity());
                sd.setCancelable(true);
                //sd.setCanceledOnTouchOutside(true);
                sd.setTitleText(getResources().getString(R.string.qingguahao));
                sd.show();
                sd.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        getActivity().finish();
                    }
                });
            }

        }
    }

    /**
     * chat row provider
     *
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
            //which is used to count the number of different chat row
            return 12;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if(message.getType() == EMMessage.Type.TXT){
                //voice call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                }else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
                    //video call
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
                //red packet code : 红包消息、红包回执消息以及转账消息的chatrow type

                //end of red packet code
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {

            return null;
        }

    }



}

