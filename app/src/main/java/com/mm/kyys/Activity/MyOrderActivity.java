package com.mm.kyys.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mm.kyys.R;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Wighet.XlTitle;

/**
 * Created by 27740 on 2017/2/15.
 */

public class MyOrderActivity extends BaseActivity {

    private XlTitle title;
    private TextView tv_section,tv_doctor;
    private RecyclerView rv;
    private Activity oThis;

    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_ordertimeactivity);
        init();
        inevent();
    }

    private void init(){

        oThis = this;

        title = (XlTitle) findViewById(R.id.ordertime_title);
        tv_section = (TextView) findViewById(R.id.ordertime_tv_section);
        tv_doctor = (TextView) findViewById(R.id.ordertime_rv_time);
        rv = (RecyclerView) findViewById(R.id.ordertime_rv_time);

    }

    private void inevent(){

        title.setText(getResources().getString(R.string.yuyueguahao));
        title.setleftText(R.string.fanhui);
        title.setLeftVisibility(true);
        title.setleftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oThis.finish();
                MyUtil.getIntance().ActivityStopAmni(oThis);
            }
        });

        llm = new LinearLayoutManager(oThis);
        llm.setOrientation(OrientationHelper.VERTICAL);
        rv.setLayoutManager(llm);


    }

}
