package com.mm.kyys.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mm.kyys.Adapter.DiseaseClassftyAdapter;
import com.mm.kyys.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 27740 on 2017/1/9.
 */

public class ClasstfyDiseaseActivity extends Activity {


    private Activity oThis;
    private RecyclerView recyclerView;
    private DiseaseClassftyAdapter adapter;
    private List<String> list_str = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseaseclassify);
        init();
        inevent();
    }

    private void init(){

        oThis = this;

        recyclerView = (RecyclerView) findViewById(R.id.diseaseclasstfy_recyclerview);

    }

    private void inevent(){


        initData();
        adapter = new DiseaseClassftyAdapter(oThis,list_str);

        adapter.setOnItemClickListener(new DiseaseClassftyAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(oThis,"按"+list_str.get(position),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(int position) {
                Toast.makeText(oThis,"长按"+list_str.get(position),Toast.LENGTH_LONG).show();
            }
        });
        LinearLayoutManager manage = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(manage);
        manage.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {

        list_str.add("1");
        list_str.add("2");
        list_str.add("3");
        list_str.add("4");
        list_str.add("5");
        list_str.add("6");
        list_str.add("7");

    }

}
