package com.mm.kyys.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mm.kyys.R;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Util.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 27740 on 2016/9/23.
 */

public class GuideActivity extends Activity implements OnPageChangeListener,View.OnTouchListener{

    private Activity oThis;
    private ViewPager viewpager;
    private ImageView[] arr_tips;
    private List<View> list_view;
    private int[] arr_imgid;
    private TextView tv_into;
    private boolean CanIntoApp = false;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_guide);
        oThis = this;
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
        viewpager = (ViewPager) findViewById(R.id.viewPage);

        arr_imgid = new int[]{R.mipmap.guide_one,R.mipmap.guide_two,R.mipmap.guide_three};

        arr_tips = new ImageView[arr_imgid.length];
        for (int i=0;i<arr_tips.length;i++){
            ImageView imageview = new ImageView(this);
            imageview.setLayoutParams(new ViewGroup.LayoutParams(10,10));
            arr_tips[i] = imageview;
            if(i==0){
                arr_tips[i].setBackgroundResource(R.mipmap.page_indicator_focused);
            }else{
                arr_tips[i].setBackgroundResource(R.mipmap.page_indicator);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            group.addView(imageview,layoutParams);
        }

        list_view = new ArrayList<View>();
        View view3 = LayoutInflater.from(oThis).inflate(R.layout.guidethree,null);

        list_view.add(LayoutInflater.from(oThis).inflate(R.layout.guideone,null));
        list_view.add(LayoutInflater.from(oThis).inflate(R.layout.guidetwo,null));
        list_view.add(view3);

        tv_into = (TextView) view3.findViewById(R.id.guidethree_tv);
        tv_into.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("xl", "进入应用被点击");
                if (SharedPreferencesManager.getIntance(oThis).getUserHasLogin()){
                    MyUtil.getIntance().ToActivity(oThis,MainActivity.class,false,null);
                }else{
                    MyUtil.getIntance().ToActivity(oThis,LoginActivity.class,false,null);
                }
            }
        });

/*        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i=0; i<arr_imgid.length;i++){
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(layoutParams);
            iv.setImageResource(arr_imgid[i]);
            list_view.add(iv);

        }*/

        viewpager.setAdapter(new MyAdapter());
        viewpager.setOnPageChangeListener(this);
        //viewpager.setCurrentItem((arr_img.length));
    }


    public class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return list_view.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            Log.i("xl", "界面是否生成"+(view==object));
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(list_view.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(list_view.get(position),0);
            return list_view.get(position);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position % 3==2){
            CanIntoApp = true;
        }else{
            CanIntoApp = false;
        }
        setImageBackground(position % 3);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setImageBackground(int selectItems){
        for (int i = 0;i<arr_tips.length;i++){
            if (i == selectItems){
                arr_tips[i].setBackgroundResource(R.mipmap.page_indicator_focused);
            }else{
                arr_tips[i].setBackgroundResource(R.mipmap.page_indicator);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("xl", "点击");
                if(CanIntoApp){
                    Log.i("xl", "Can into app");

                }
                break;
        }
        if (CanIntoApp&&event.getAction()==MotionEvent.ACTION_DOWN){

        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("xl", "onKeyDown: ");
        return super.onKeyDown(keyCode, event);
    }



}
