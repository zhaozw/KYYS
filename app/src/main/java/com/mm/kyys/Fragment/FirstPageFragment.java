package com.mm.kyys.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.mm.kyys.Activity.ChatActivity;
import com.mm.kyys.Activity.DiseaseActivity;
import com.mm.kyys.Activity.NoticActivity;
import com.mm.kyys.Activity.RegisterBySelfActivity;
import com.mm.kyys.Adapter.MyPagerAdapter;
import com.mm.kyys.R;
import com.mm.kyys.Util.AllData;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Wighet.XlTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sexyXu on 2016/10/25.
 */

public class FirstPageFragment extends BaseFragment implements View.OnClickListener{

    private String TAG = "首页";
    private Activity oThis;
    private Button btn_connnectdoctor,btn_notice;
    private LinearLayout layout_yygh,layout_mfzx,layout_zjwz;

    private ViewPager vp;
    private LinearLayout layout_viewpager;
    private MyPagerAdapter mypageradapter;
    private List<View> list_view = new ArrayList<View>();
    private int count = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState)
        View view = inflater.inflate(R.layout.fragment_firstpage,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        inevent();
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){

            case R.id.firstpage_layout_yygh:
                MyUtil.getIntance().ToActivity(oThis, RegisterBySelfActivity.class,true,null);
                getActivity().overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_from_left);
                break;
            case R.id.firstpage_layout_mfzx:
                MyUtil.getIntance().ToActivity(oThis, DiseaseActivity.class,true,null);
                getActivity().overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_from_left);
                break;
            case R.id.firstpage_layout_zjwz:
                //MyUtil.getIntance().ToActivity(oThis, NoticActivity.class,true,null);
                break;
        }
    }

    private void init(){
        oThis = getActivity();

        vp = (ViewPager) oThis.findViewById(R.id.fragment_firstpage_viewPage);
        layout_viewpager = (LinearLayout) oThis.findViewById(R.id.firstpage_layout_viewpager);

        layout_yygh = (LinearLayout) getView().findViewById(R.id.firstpage_layout_yygh);
        layout_mfzx = (LinearLayout) getView().findViewById(R.id.firstpage_layout_mfzx);
        layout_zjwz = (LinearLayout) getView().findViewById(R.id.firstpage_layout_zjwz);

        layout_yygh.setOnClickListener(this);
        layout_mfzx.setOnClickListener(this);
        layout_zjwz.setOnClickListener(this);

    }

    private void inevent() {

        SetViewPager();

    }

    private void SetViewPager(){
        DisplayMetrics dm = new DisplayMetrics();
        oThis.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int height = dm.heightPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) layout_viewpager.getLayoutParams();
        lp.height = height / 4;
        layout_viewpager.setLayoutParams(lp);

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView tv1 = new TextView(oThis);
        //tv1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        tv1.setBackgroundResource(R.mipmap.pic_1);
        tv1.setLayoutParams(lp1);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView tv2 = new TextView(oThis);
        tv2.setBackgroundResource(R.mipmap.pic_2);
        tv2.setLayoutParams(lp2);

        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        TextView tv3 = new TextView(oThis);
        tv3.setBackgroundResource(R.mipmap.pic_3);
        tv3.setLayoutParams(lp3);

        list_view.add(tv1);
        list_view.add(tv2);
        list_view.add(tv3);

        mypageradapter = new MyPagerAdapter(list_view);
        vp.setAdapter(mypageradapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.i(TAG, "onPageScrolled--"+position+"--"+positionOffset+"--"+positionOffsetPixels);
                timer.start();
            }

            @Override
            public void onPageSelected(int position) {
                //Log.i(TAG, "onPageSelected--"+position);
                count = position;
                timer.start();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.i(TAG, "onPageScrollStateChanged--"+state);
            }
        });
    }

    private CountDownTimer timer = new CountDownTimer(5000,1000){

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if(count == list_view.size()-1){
                vp.setCurrentItem(0,false);
                count = 0;
            }else{
                vp.setCurrentItem(++count);
            }
        }
    };


}
