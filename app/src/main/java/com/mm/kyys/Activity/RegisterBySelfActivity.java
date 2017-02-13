package com.mm.kyys.Activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.mm.kyys.Adapter.SectionAdapter;
import com.mm.kyys.Adapter.SectionAdapter.OnSectionItemClickListener;
import com.mm.kyys.Adapter.SectorAdapter;
import com.mm.kyys.Model.Section;
import com.mm.kyys.R;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Wighet.XlTitle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 27740 on 2017/1/13.
 */

public class RegisterBySelfActivity extends BaseActivity{

    public static final String SECTION_NAME = "section_name";

    private Activity oThis;

    private RecyclerView rv_section;
    private RecyclerView rv_doctor;
    private RecyclerView rv_sector;
    private LinearLayoutManager llm_section;
    private LinearLayoutManager llm_doctor;
    private GridLayoutManager glm_sector;
    private SectionAdapter sectionAdapter;
    private SectorAdapter sectorAdapter;

    private List<Section> list_section = new ArrayList<Section>();

    private XlTitle title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerbyself);
        init();
        inevent();
    }


    private void init() {

        oThis = this;

        rv_section = (RecyclerView) findViewById(R.id.registerbyself_rv_section);
        rv_doctor = (RecyclerView) findViewById(R.id.registerbyself_rv_doctor);
        rv_sector = (RecyclerView) findViewById(R.id.registerbyself_rv_sector);

        title = (XlTitle) findViewById(R.id.registerbyself_title);


    }

    private void inevent() {

        title.setText(getResources().getString(R.string.yuyueguahao));
        title.setleftText(R.string.shouye);
        title.setLeftVisibility(true);
        title.setRightVisibility(false);
        title.setleftText(R.string.fanhui);
        title.setleftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        glm_sector = new GridLayoutManager(oThis,4,OrientationHelper.VERTICAL,false);
        rv_sector.setLayoutManager(glm_sector);
        getData();
        sectorAdapter = new SectorAdapter(oThis,list_section);
        rv_sector.setAdapter(sectorAdapter);
        sectorAdapter.setOnSectorItemClickListener(new SectorAdapter.OnSectorItemClickListener() {
            @Override
            public void OnClick(int position) {
                Section section = list_section.get(position);
                Bundle bundle = new Bundle();
                bundle.putString(SECTION_NAME,section.getName());
                MyUtil.getIntance().ToActivity(oThis,SelectDoctorActvity.class,true,bundle);
                oThis.overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_from_left);
            }
        });


    }

    private void getData(){
        list_section.add(new Section("骨科",1));
        list_section.add(new Section("儿科",2));
        list_section.add(new Section("妇产科",3));
        list_section.add(new Section("口腔科",4));
        list_section.add(new Section("男科",5));
        list_section.add(new Section("心血管内科",6));
        list_section.add(new Section("消化内科",7));
        list_section.add(new Section("皮肤科",8));
        list_section.add(new Section("神经内科",9));
        list_section.add(new Section("耳鼻咽喉科",10));
        list_section.add(new Section("泌尿外科",11));
        list_section.add(new Section("神经外科",12));
        list_section.add(new Section("乳腺外科",13));
        list_section.add(new Section("眼科",14));
        list_section.add(new Section("普外科",15));
        list_section.add(new Section("感染科",16));
        list_section.add(new Section("整形外科",17));
        list_section.add(new Section("妇科",18));
        list_section.add(new Section("外科",19));
        list_section.add(new Section("呼吸内科",20));
    }

    @Override
    public void back() {
        super.back();
        oThis.finish();
        oThis.overridePendingTransition(R.anim.fragment_slide_in_from_left,R.anim.fragment_slide_out_from_right);
    }
}
