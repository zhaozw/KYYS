package com.mm.kyys.Activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.telephony.SubscriptionInfo;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mm.kyys.Adapter.SectionAdapter;
import com.mm.kyys.Adapter.SectionAdapter.OnSectionItemClickListener;
import com.mm.kyys.Adapter.SectorAdapter;
import com.mm.kyys.DB.DBManager;
import com.mm.kyys.DB.DBopenHelper;
import com.mm.kyys.Model.Section;
import com.mm.kyys.R;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Util.RestClient;
import com.mm.kyys.Wighet.XlTitle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import cz.msebera.android.httpclient.Header;

/**
 * Created by 27740 on 2017/1/13.
 */

public class SelectSectionActivity extends BaseActivity{

    public static final String SECTION_NAME = "section_name";

    private Activity oThis;

    private RecyclerView rv_section_parent;     //科室一级分类列表
    private RecyclerView rv_section_child;      //科室二级分类列表

    private LinearLayoutManager llm_section_parent;     //科室一级分类列表布局管理器
    private LinearLayoutManager llm_section_child;      //科室二级分类列表布局管理器

    private SectionAdapter sectionAdapter_parent;       //科室一级分类列表适配器
    private SectionAdapter sectionAdapter_child;        //科室二级分类列表适配器

    private List<Section> list_section_parent = new ArrayList<Section>();       //科室一级分类列表数据
    private List<Section> list_section_child = new ArrayList<Section>();        //科室二级分类列表数据

    private List<Boolean> list_ischeck_child = new ArrayList<Boolean>();        //科室一级分类列表选中情况
    private List<Boolean> list_ischeck_parent = new ArrayList<Boolean>();       //科室二级分类列表选中情况

    private int parent_last_index = 0;      //科室一级分类列表 上次 被选中项的下标
    private int child_last_index = 0;       //科室二级分类列表 上次 被选中项的下标

    private int parent_current_index = 0;       //科室一级分类列表 当前 被选中项的下标
    private int child_current_index = 0;        //科室二级分类列表 当前 被选中项的下标
    private XlTitle title;

    private DBopenHelper dBopenHelper;
    private SQLiteDatabase readable_database,writable_database;



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

        dBopenHelper = DBopenHelper.getIntance(oThis);
        readable_database = dBopenHelper.getReadableDatabase();
    }

    private void inevent() {

        title.setText(getResources().getString(R.string.xuanzekeshi));
        title.setLeftVisibility(true);
        title.setRightVisibility(false);
        title.setleftText(R.string.fanhui);
        title.setleftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        //RecyclerView设置
        llm_section_parent = new LinearLayoutManager(oThis);
        llm_section_child = new LinearLayoutManager(oThis);
        llm_section_parent.setOrientation(OrientationHelper.VERTICAL);
        llm_section_child.setOrientation(OrientationHelper.VERTICAL);
        rv_section_parent.setLayoutManager(llm_section_parent);
        rv_section_child.setLayoutManager(llm_section_child);

        //从数据库获取数据
        getDate_Parent();
        getDate_child(list_section_parent.get(0).getDid());

        //科室一级分类绑定适配器
        sectionAdapter_parent = new SectionAdapter(oThis,list_section_parent,list_ischeck_parent);
        rv_section_parent.setAdapter(sectionAdapter_parent);

        //科室一级分类item点击监听
        sectionAdapter_parent.setOnSectionItemClickListener(new SectionAdapter.OnSectionItemClickListener() {
            @Override
            public void onClick(int position, TextView tv) {
                FreshFristMenuUi(position);
                //获取点击相的子分类
                getDate_child(list_section_parent.get(position).getDid());
                sectionAdapter_child = new SectionAdapter(oThis,list_section_child,list_ischeck_child);
                rv_section_child.setAdapter(sectionAdapter_child);
                //子分类点击监听
                sectionAdapter_child.setOnSectionItemClickListener(new SectionAdapter.OnSectionItemClickListener() {
                    @Override
                    public void onClick(int position, TextView tv) {
                        //科室二级分类项点击监听
                        FreshSecondMenuUi(position);
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

            @Override
            public void onLongClick(int position) {

            }
        });


    }

    private void FreshFristMenuUi(int position){
        child_current_index = 0;
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

    private void FreshSecondMenuUi(int position){
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
    }

    private void getDate_Parent(){

        list_section_parent = DBManager.getInstance().QuerySection(1,"0",readable_database);
        Log.e("xl", "Parent NO1: "+list_section_parent.get(0).toString() );
        for (int i=0;i<list_section_parent.size();i++){
            if (i==0){
                list_ischeck_parent.add(i,true);
            }else{
                list_ischeck_parent.add(i,false);
            }
        }
    }

    private void getDate_child(String parent_id){
        Log.e("xl", "父id"+parent_id);
        list_section_child = DBManager.getInstance().QuerySection(2,parent_id,readable_database);
        for (int j=0;j<list_section_child.size();j++){
            list_ischeck_child.add(j,false);
        }
    }

    @Override
    public void back() {
        
        super.back();
        oThis.finish();
        oThis.overridePendingTransition(R.anim.fragment_slide_in_from_left,R.anim.fragment_slide_out_from_right);
    }
}
