package com.mm.kyys.Activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mm.kyys.Adapter.SectionAdapter;
import com.mm.kyys.Adapter.SectionAdapter.OnSectionItemClickListener;
import com.mm.kyys.Adapter.SectorAdapter;
import com.mm.kyys.Model.Section;
import com.mm.kyys.R;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Util.RestClient;
import com.mm.kyys.Wighet.XlTitle;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 27740 on 2017/1/13.
 */

public class SelectSectionActivity extends BaseActivity{

    public static final String SECTION_NAME = "section_name";

    private Activity oThis;

    private RecyclerView rv_section_parent;
    private RecyclerView rv_section_child;
    private RecyclerView rv_sector;
    private LinearLayoutManager llm_section_parent;
    private LinearLayoutManager llm_section_child;
    //private GridLayoutManager glm_sector;
    private SectionAdapter sectionAdapter_parent;
    private SectionAdapter sectionAdapter_child;
    //private SectorAdapter sectorAdapter;

    private List<Section> list_section_parent = new ArrayList<Section>();
    private List<Boolean> list_ischeck_parent = new ArrayList<Boolean>();
    private List<Section> list_section_child = new ArrayList<Section>();
    private List<Boolean> list_ischeck_child = new ArrayList<Boolean>();

    private int parent_last_index = 0;
    private int child_last_index = 0;
    private int parent_current_index = 0;
    private int child_current_index = 0;
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

        rv_section_parent = (RecyclerView) findViewById(R.id.registerbyself_rv_section_parent);
        rv_section_child = (RecyclerView) findViewById(R.id.registerbyself_rv_section_child);
        //rv_sector = (RecyclerView) findViewById(R.id.registerbyself_rv_sector);

        title = (XlTitle) findViewById(R.id.registerbyself_title);


    }

    private void inevent() {

        title.setText(getResources().getString(R.string.yuyueguahao));
        title.setLeftVisibility(true);
        title.setRightVisibility(false);
        title.setleftText(R.string.fanhui);
        title.setleftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        rv_section_child.setVisibility(View.GONE);

        //glm_sector = new GridLayoutManager(oThis,4,OrientationHelper.VERTICAL,false);
        llm_section_parent = new LinearLayoutManager(oThis);
        llm_section_child = new LinearLayoutManager(oThis);
        llm_section_parent.setOrientation(OrientationHelper.VERTICAL);
        llm_section_child.setOrientation(OrientationHelper.VERTICAL);
        rv_section_parent.setLayoutManager(llm_section_parent);
        rv_section_child.setLayoutManager(llm_section_child);
        getDate_Parent();
        getDate_child();
        sectionAdapter_parent = new SectionAdapter(oThis,list_section_parent,list_ischeck_parent);
        sectionAdapter_child = new SectionAdapter(oThis,list_section_child,list_ischeck_child);
        //sectorAdapter = new SectorAdapter(oThis,list_section);
        rv_section_parent.setAdapter(sectionAdapter_parent);
        rv_section_child.setAdapter(sectionAdapter_child);
        sectionAdapter_parent.setOnSectionItemClickListener(new SectionAdapter.OnSectionItemClickListener() {
            @Override
            public void onClick(int position, TextView tv) {
                list_section_child.clear();
                getDate_child();
                sectionAdapter_child.notifyDataSetChanged();

                child_current_index = 0;
                rv_section_child.setVisibility(View.VISIBLE);
                parent_last_index = parent_current_index;
                list_ischeck_parent.set(parent_last_index,false);
                parent_current_index = position;
                if (parent_current_index!=0){
                    list_ischeck_parent.set(0,false);
                    list_ischeck_parent.set(position,true);
                }else{
                    list_ischeck_parent.set(0,true);
                }
                sectionAdapter_parent.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(int position) {

            }
        });

        sectionAdapter_child.setOnSectionItemClickListener(new SectionAdapter.OnSectionItemClickListener() {
            @Override
            public void onClick(int position, TextView tv) {

                child_last_index = child_current_index;
                list_ischeck_child.set(child_last_index,false);
                child_current_index = position;
                if (child_current_index!=0){
                    list_ischeck_child.set(0,false);
                    list_ischeck_child.set(position,true);
                }else{
                    list_ischeck_child.set(0,true);
                }
                sectionAdapter_child.notifyDataSetChanged();
                Section section = list_section_child.get(position);
                Bundle bundle = new Bundle();
                bundle.putString(SECTION_NAME,section.getName());
                MyUtil.getIntance().ToActivity(oThis,SelectDoctorActvity.class,true,bundle);
                oThis.overridePendingTransition(R.anim.fragment_slide_in_from_right,R.anim.fragment_slide_out_from_left);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }

    private void getDate_Parent(){

        list_section_parent.add(new Section("骨科",1));
        list_section_parent.add(new Section("儿科",2));
        list_section_parent.add(new Section("妇产科",3));
        list_section_parent.add(new Section("口腔科",4));
        list_section_parent.add(new Section("男科",5));
        list_section_parent.add(new Section("心血管内科",6));
        list_section_parent.add(new Section("消化内科",7));

        for (int i=0;i<7;i++){
            if (i==0){
                list_ischeck_parent.add(i,true);
            }else{
                list_ischeck_parent.add(i,false);
            }
        }


        RequestParams params = new RequestParams();
        RestClient.get("sel_Department.ashx",params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("xl", "科室信息获取失败");

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("xl", "科室信息："+response);

            }
        });
    }


    private void getDate_child(){
        list_section_child.add(new Section("皮肤科",8));
        list_section_child.add(new Section("神经内科",9));
        list_section_child.add(new Section("耳鼻咽喉科",10));
        list_section_child.add(new Section("泌尿外科",11));
        list_section_child.add(new Section("神经外科",12));
        list_section_child.add(new Section("乳腺外科",13));
        list_section_child.add(new Section("眼科",14));
        list_section_child.add(new Section("普外科",15));
        list_section_child.add(new Section("感染科",16));
        list_section_child.add(new Section("整形外科",17));
        list_section_child.add(new Section("妇科",18));
        list_section_child.add(new Section("外科",19));
        list_section_child.add(new Section("呼吸内科",20));
        for (int j=0;j<13;j++){
            if (j==0){
                list_ischeck_child.add(j,true);
            }else{
                list_ischeck_child.add(j,false);
            }
        }
    }

    @Override
    public void back() {
        super.back();
        oThis.finish();
        oThis.overridePendingTransition(R.anim.fragment_slide_in_from_left,R.anim.fragment_slide_out_from_right);
    }
}
