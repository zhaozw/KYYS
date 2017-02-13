package com.mm.kyys.Activity;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.Collator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.mm.kyys.Fragment.FirstPageFragment;
import com.mm.kyys.Fragment.MineFragment;
import com.mm.kyys.Fragment.RecordFragment;
import com.mm.kyys.Fragment.ServiceFragment;

import com.mm.kyys.Interface.OppositeResponse;
import com.mm.kyys.R;
import com.mm.kyys.Util.AllData;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Wighet.XlDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener{



    private Activity oThis;
    private FrameLayout framelayout;
    private LinearLayout tab1,tab2,tab3,tab4;
    private ImageView iv_tab1,iv_tab2,iv_tab3,iv_tab4;
    private TextView tv_tab1,tv_tab2,tv_tab3,tv_tab4;
    private TextView tv_dot;
    private int current_index = 0;
    private int before_index = 0;
    private Fragment[] fragment_arr = new Fragment[4];

    private ImageView[] iv_arr = new ImageView[4];
    private TextView[] tv_arr = new TextView[4];
    private long exitTime = 0;
    private FirstPageFragment firsrpage_fragment;
    private ServiceFragment service_fragment;
    private MineFragment mine_fragment;
    private RecordFragment record_fragment;
    public static int SCREEN_HEIGHT = 0;
    public static int SCREEN_WIGHT = 0;

    private LocalBroadcastManager broadcastManager;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        inevent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mainactivity_tab1:

                current_index = 0;
                break;
            case R.id.mainactivity_tab2:

                current_index = 1;
                break;
            case R.id.mainactivity_tab3:

                current_index = 2;
                break;
            case R.id.mainactivity_tab4:

                current_index = 3;
                break;
        }
        iv_arr[before_index].setSelected(false);
        iv_arr[current_index].setSelected(true);

        tv_arr[before_index].setTextColor(getResources().getColor(R.color.black_deep));
        tv_arr[current_index].setTextColor(getResources().getColor(R.color.bottom_icon_selected));

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        //设置切换fragment动画
        if(before_index<current_index){
            t.setCustomAnimations(
                    R.anim.fragment_slide_in_from_right,
                    R.anim.fragment_slide_out_from_left,
                    R.anim.fragment_slide_in_from_right,
                    R.anim.fragment_slide_out_from_left);
        }else{
            t.setCustomAnimations(
                    R.anim.fragment_slide_in_from_left,
                    R.anim.fragment_slide_out_from_right,
                    R.anim.fragment_slide_in_from_left,
                    R.anim.fragment_slide_out_from_right);
        }



        t.hide(fragment_arr[before_index]).show(fragment_arr[current_index]);
        t.commit();
        before_index = current_index;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            if ((System.currentTimeMillis() - exitTime) >= 2000) {
                Log.i("xl", "spaceTime>2000");
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.tuichukangyunyisheng),Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {
                Log.i("xl", "spaceTime<2000");

                finish();
                //System.exit(0);
            }
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    public void init() {
        oThis = this;
        //registerBroadcastReceiver();
        MyUtil.getIntance().addActivity(oThis);
        registerBroadcastReceiver();

        Display d = oThis.getWindowManager().getDefaultDisplay();
        SCREEN_WIGHT = d.getWidth();
        SCREEN_HEIGHT = d.getHeight();

        tv_dot = (TextView) findViewById(R.id.mainactivity_dot);

        framelayout = (FrameLayout) findViewById(R.id.mainactivity_framelayout);
        tab1 = (LinearLayout) findViewById(R.id.mainactivity_tab1);
        tab2 = (LinearLayout) findViewById(R.id.mainactivity_tab2);
        tab3 = (LinearLayout) findViewById(R.id.mainactivity_tab3);
        tab4 = (LinearLayout) findViewById(R.id.mainactivity_tab4);

        iv_tab1 = (ImageView) findViewById(R.id.mainactivity_iv_tab1);
        iv_tab2 = (ImageView) findViewById(R.id.mainactivity_iv_tab2);
        iv_tab3 = (ImageView) findViewById(R.id.mainactivity_iv_tab3);
        iv_tab4 = (ImageView) findViewById(R.id.mainactivity_iv_tab4);

        tv_tab1 = (TextView) findViewById(R.id.mainactivity_tv_tab1);
        tv_tab2 = (TextView) findViewById(R.id.mainactivity_tv_tab2);
        tv_tab3 = (TextView) findViewById(R.id.mainactivity_tv_tab3);
        tv_tab4 = (TextView) findViewById(R.id.mainactivity_tv_tab4);

        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);


    }

    public void inevent() {
        firsrpage_fragment = new FirstPageFragment();
        service_fragment = new ServiceFragment();
        record_fragment = new RecordFragment();
        mine_fragment = new MineFragment();

        fragment_arr[0] = firsrpage_fragment;
        fragment_arr[1] = service_fragment;
        fragment_arr[2] = record_fragment;
        fragment_arr[3] = mine_fragment;

        iv_arr[0] = iv_tab1;
        iv_arr[1] = iv_tab2;
        iv_arr[2] = iv_tab3;
        iv_arr[3] = iv_tab4;

        tv_arr[0] = tv_tab1;
        tv_arr[1] = tv_tab2;
        tv_arr[2] = tv_tab3;
        tv_arr[3] = tv_tab4;

        tv_arr[0].setTextColor(getResources().getColor(R.color.bottom_icon_selected));
        tv_arr[1].setTextColor(getResources().getColor(R.color.black_deep));
        tv_arr[2].setTextColor(getResources().getColor(R.color.black_deep));
        tv_arr[3].setTextColor(getResources().getColor(R.color.black_deep));
        iv_arr[0].setSelected(true);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction t = fm.beginTransaction();
        t.add(R.id.mainactivity_framelayout,firsrpage_fragment)
                .add(R.id.mainactivity_framelayout,service_fragment).add(R.id.mainactivity_framelayout,record_fragment).add(R.id.mainactivity_framelayout,mine_fragment)
                .hide(service_fragment).hide(mine_fragment).hide(record_fragment)
                .show(firsrpage_fragment);
        t.commit();

    }

    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(oThis);
        IntentFilter filter = new IntentFilter();
        filter.addAction(AllData.RECEIVE_MESSAGE);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action) {
                    case AllData.RECEIVE_MESSAGE:
                        Log.e("xl", "个人中心红点显示");
                        tv_dot.setVisibility(View.VISIBLE);
                        mine_fragment.fresh();
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
