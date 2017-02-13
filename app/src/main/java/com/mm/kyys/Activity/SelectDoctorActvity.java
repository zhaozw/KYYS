package com.mm.kyys.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.mm.kyys.Adapter.SelectDoctorAdapter;
import com.mm.kyys.Model.Doctor;
import com.mm.kyys.R;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Wighet.XlTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 27740 on 2017/1/16.
 */

public class SelectDoctorActvity extends Activity {

    private Activity oThis;
    private RecyclerView rv_doctor;
    private LinearLayoutManager llm;
    private SelectDoctorAdapter adapter;
    private XlTitle title;
    private List<Doctor> list_doctor = new ArrayList<Doctor>();


    private String setion_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectdoctor);
        init();
        inevent();
    }

    private void init(){

        oThis= this;
        Bundle bundle = getIntent().getExtras();
        setion_name = bundle.getString(RegisterBySelfActivity.SECTION_NAME);

        rv_doctor = (RecyclerView) findViewById(R.id.selectdoctor_rv);
        title = (XlTitle) findViewById(R.id.selectdoctor_title);


    }

    private void inevent(){

        title.setText(setion_name);
        title.setleftText(R.string.fanhui);
        title.setrightText(R.string.shouye);
        title.setleftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oThis.finish();
                oThis.overridePendingTransition(R.anim.fragment_slide_in_from_left,R.anim.fragment_slide_out_from_right);
            }
        });
        title.setrightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(oThis,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        llm = new LinearLayoutManager(oThis);
        llm.setOrientation(OrientationHelper.VERTICAL);
        rv_doctor.setLayoutManager(llm);
        getData();
        adapter = new SelectDoctorAdapter(oThis,list_doctor);
        rv_doctor.setAdapter(adapter);

        adapter.setOnDoctorClickListener(new SelectDoctorAdapter.OnDoctorClickListener() {
            @Override
            public void OnClick(int position) {
                MyUtil.getIntance().ToActivity(oThis,DoctorDetialActivity.class,true,null);
                oThis.overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_from_left);

            }
        });

    }
    //https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3018777119,2532860069&fm=21&gp=0.jpg
    private void getData(){
        list_doctor.add(new Doctor("段志宇","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3018777119,2532860069&fm=21&gp=0.jpg",6,"各种疾病诊断","主任医师"));
        list_doctor.add(new Doctor("段志宇","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3018777119,2532860069&fm=21&gp=0.jpg",6,"各种疾病诊断","主任医师"));
        list_doctor.add(new Doctor("段志宇","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3018777119,2532860069&fm=21&gp=0.jpg",6,"各种疾病诊断","主任医师"));
        list_doctor.add(new Doctor("段志宇","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3018777119,2532860069&fm=21&gp=0.jpg",6,"各种疾病诊断","主任医师"));
    }

}
