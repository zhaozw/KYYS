package com.mm.kyys.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.mm.kyys.Model.User;

/**
 * Created by 27740 on 2017/1/16.
 */

public class SharedPreferencesManager {


    public static SharedPreferencesManager intance;
    SharedPreferences sp;

    public static SharedPreferencesManager getIntance(Context context){
        if (intance == null){
            intance = new SharedPreferencesManager(context);
        }
        return intance;
    }

    public SharedPreferencesManager(Context context){
        sp = context.getSharedPreferences("config",context.MODE_PRIVATE);
    }
    //保存挂号信息  键：用户id_医生id
    public void setRegisterInfo(String uid_did,Boolean hadRegister){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(uid_did,hadRegister);
        editor.commit();
    }
    //读取挂号信息
    public Boolean getRegisterInfo(String uid_did){
        if (sp!=null){
            return sp.getBoolean(uid_did,false);
        }else{
         return false;
        }
    }

    //保存用户是否登录
    public void setUserHasLogin(boolean HasLogin){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("haslogin",HasLogin);
        editor.commit();
    }
    //读取用户是否登录
    public Boolean getUserHasLogin(){
        if (sp!=null){
            return sp.getBoolean("haslogin",false);
        }else{
            return false;
        }
    }

    //保存当前使用者信息
    public void setUserInfo(Context oThis, String userInfoStr) {

        sp = oThis.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        //传入userinfostr不为空保存到shardpreferences  否则清空shardpreferences
        if (!TextUtils.isEmpty(userInfoStr)) {
            editor.putString("userInfo", userInfoStr);
        } else {
            editor.clear();
        }
        editor.commit();
    }
    //读取已经保存的当前使用者信息
    public User getUserInfo(Context context) {

        sp = context.getSharedPreferences("userInfo",
                Context.MODE_PRIVATE);

        String jsonStr = sp.getString("userInfo", "");

        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        } else {
            return JSON.parseObject(jsonStr, User.class);
        }
    }

    //保存用户信息（昵称和头像）
    public void setUserNickPic(String user_id,String userinfo,Context context){
        sp = context.getSharedPreferences("user_nick_pic",
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        //传入userinfostr不为空保存到shardpreferences  否则清空shardpreferences
        if (!TextUtils.isEmpty(userinfo)) {
            editor.putString(user_id+"info",userinfo);
        } else {
            editor.clear();
        }
        editor.commit();
    }
    //读取用户信息（昵称和头像）
    public String getUserNickPic(String user_id,Context context){
        sp = context.getSharedPreferences("user_nick_pic",
                Context.MODE_PRIVATE);

        String userinfo = sp.getString(user_id+"info","");
        return userinfo;
    }
}
