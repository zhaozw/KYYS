package com.mm.kyys.Util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mm.kyys.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sexyXu on 2016/10/28.
 */

public class MyUtil {

    private List<Activity> activityList = new LinkedList<Activity>();
    public static MyUtil intance;

    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;

    public static MyUtil getIntance(){
        if (intance==null){
            intance = new MyUtil();
        }

        return intance;
    }

    //不传递参数的anctivity跳转
    public void ToActivity(Activity Othis, Class otherActicity, boolean canReturn, Bundle bundle){
        Intent intent = new Intent();
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        intent.setClass(Othis,otherActicity);
        Othis.startActivity(intent);

        if (!canReturn){
            Othis.finish();
        }
    }


    //判断输入框内容是否为空
    public Boolean EtTextIsNull(EditText et){
        if(et.getText()==null||"".equals(et.getText())){
            return true;
        }else{
            return false;
        }
    }

    //获取当前运行activity名称
    public String getRunningActivityName(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return am.getRunningTasks(1).get(0).topActivity.getClassName();
    }

    //判断是不是手机号
    public boolean isMobileNum(String str_phonenumber){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m =p.matcher(str_phonenumber);
        return m.matches();
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    // 遍历所有Activity出mainactivity其余finish
    public void ExitElseMain() {
        for (Activity activity : activityList) {
            if(!activity.getClass().toString().equals("class com.mm.kyys.Activity.MainActivity")){
                activity.finish();
            }
        }
    }

    //唤醒屏幕并解锁
    public static void wakeUpAndUnlock(Context context){
        KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK,"bright");
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
    }

    public void Back(Activity oThis){
        oThis.finish();
    }

    //控制免提
    public void setSpeakerphoneOn(boolean on,Activity oThis) {
        audioManager = (AudioManager)oThis.getSystemService(Context.AUDIO_SERVICE);
        if (on) {
            audioManager.setSpeakerphoneOn(true);
        } else {
            audioManager.setSpeakerphoneOn(false);//关闭扬声器
            audioManager.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
            oThis.setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            //把声音设定成Earpiece（听筒）出来，设定为正在通话中
            audioManager.setMode(AudioManager.MODE_IN_CALL);
        }
    }

    //将res下的raw中的文件导入到手机存储空间
    public void copyFromRaw(Context context,int which_res,String res_new_name) {
        String path = getQuestionBankPath(res_new_name);
        try {
            InputStream is = context.getResources().openRawResource(which_res); // 欲导入的数据库
            //如果目标数据库在手机上不存在则导入
            if (!(new File(path).exists())) {

                FileOutputStream fos;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[40000];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (FileNotFoundException e) {
            Log.i("xl", "FileNotFoundException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("xl", "IOException");
            e.printStackTrace();
        }
    }
    //获取文件保存路径
    public String getQuestionBankPath(String res_new_name){
        String file_name = res_new_name;
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/KYYS");
        if (!file.exists()){
            file.mkdir();
        }
        String file_path = file.getPath()+"/"+file_name;
        return file_path;
    }

    //准备播放视频请求铃声
    public void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();

        //获取mp3文件的路径
        File file = new File(Environment.getExternalStorageDirectory()+"/KYYS","video_music.mp3");
        Log.e("xl", "视频铃声存放路径"+file.getAbsolutePath().toString());
        try {
            mediaPlayer.setDataSource(file.getAbsolutePath()); //为播放器设置mp3文件的路径
            mediaPlayer.prepare(); //做好准备
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //播放视频请求铃声
    public void playVideoRing(Activity oThis){
        audioManager = (AudioManager)oThis.getSystemService(Context.AUDIO_SERVICE);
        if(!mediaPlayer.isPlaying())
        {
            mediaPlayer.start();
            Log.e("xl", "start");
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e("xl", "onCompletion");

                audioManager.setSpeakerphoneOn(true);
                mediaPlayer.start();
                mp.start();
            }
        });
    }

    public void stopVideoRing(){
        try {
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }catch (IllegalStateException e){

        }

    }

    //获取手机分辨率
    public float[] screenPix(Activity oThis){
        float[] arr = new float[2];
        WindowManager wm = oThis.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        arr[0] = width;
        arr[1] = height;
        return arr;
    }
    //时间转换为时间戳
    public Date TimeToTimeStamp(){
        String time = "2017-02-14T17:08:43";
        time = time.replace("T"," ");
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("xl", "时间转换为时间戳:"+date.getTime());
        String Time = format.format(date);
        Log.e("xl", "日期+时间："+Time);
        return date;
    }
    //时间戳转换为时间

}
