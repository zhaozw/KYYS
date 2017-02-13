package com.mm.kyys.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mm.kyys.R;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Wighet.XlTitle;

/**
 * Created by 27740 on 2017/1/9.
 */

public class DiseaseActivity extends Activity implements View.OnClickListener{


    private Activity oThis;
    private Button btn_children;
    private XlTitle title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);
        init();
        inevent();
    }

    private void init(){

        oThis = this;
        MyUtil.getIntance().addActivity(oThis);

        btn_children = (Button) findViewById(R.id.disease_btn_ye);
        title = (XlTitle) findViewById(R.id.disease_title);

        btn_children.setOnClickListener(this);

    }

    private void inevent(){

        title.setText(getResources().getString(R.string.zizhuzhenduan));
        title.setLeftVisibility(true);
        title.setRightVisibility(true);

        title.setleftText(R.string.fanhui);
        title.setrightText(R.string.shouye);
        title.setleftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtil.getIntance().Back(oThis);
                //MyUtil.getIntance().ExitElseMain();
            }
        });
        title.setrightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtil.getIntance().ExitElseMain();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.disease_btn_ye:
                MyUtil.getIntance().ToActivity(oThis,ClasstfyDiseaseActivity.class,true,null);
                break;
        }

    }

}
