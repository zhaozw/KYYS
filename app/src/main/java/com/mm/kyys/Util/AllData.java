package com.mm.kyys.Util;

import com.mm.kyys.Activity.BaseActivity;
import com.mm.kyys.R;

/**
 * Created by 27740 on 2017/1/10.
 */

public class AllData {

    public static final String IP = "http://101.201.31.32/api/";


    public static final String VIDEO_REQUEST = "xl.video_request";
    public static final String VOICE_REQUEST = "xl.voice_request";
    public static final String I_AGREE = "xl.i_agree";
    public static final String OPPO_AGREE = "xl.oppo_agree";
    public static final String CONNECTION_SUEESSFUL = "xl.connection_suessful";
    public static final String CONNECTION_SHUTDOWN = "xl.connection_shutdown";
    public static final String HAD_CONNECTIONED = "xl.had_connectioned";

    public static final String RECEIVE_MESSAGE = "receive_message";

    public static Boolean HAS_UNREAD_MESSAGE = false;


    public static final int[] SECTION_PIC = {
            R.mipmap.pic_guke, R.mipmap.pic_erke, R.mipmap.pic_fuchanke, R.mipmap.pic_kouqiang,
            R.mipmap.pic_nanke, R.mipmap.pic_xinxueguanke, R.mipmap.pic_xiaohuneike, R.mipmap.pic_pifuke,
            R.mipmap.pic_shenjingnei, R.mipmap.pic_erbiyanhouke, R.mipmap.pic_miniaowaike, R.mipmap.pic_shenjingwaike,
            R.mipmap.pic_ruxianwai, R.mipmap.pic_yanke, R.mipmap.pic_puwaike, R.mipmap.pic_ganranke,
            R.mipmap.pic_zhenxingwaike, R.mipmap.pic_fuke, R.mipmap.pic_waike, R.mipmap.pic_huxineike
    };

    //public static boolean VIDEO_VOICE_CONNECTIONED = false;

    public static AllData instance;
    private String userName;

    public static AllData getInstance(){
        if (instance==null){
            instance = new AllData();
        }
        return instance;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public int getSectionPic(int pic_no){
        return SECTION_PIC[pic_no-1];
    }

}
