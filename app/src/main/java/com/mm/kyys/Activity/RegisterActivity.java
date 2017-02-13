package com.mm.kyys.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mm.kyys.R;
import com.mm.kyys.Util.AllData;
import com.mm.kyys.Util.SharedPreferencesManager;
import com.mm.kyys.View.SweetAlertDialog;
import com.mm.kyys.Wighet.XlProgressDialog;
import com.mm.kyys.Wighet.XlTitle;

/**
 * Created by 27740 on 2017/1/16.
 */

public class RegisterActivity extends Activity {

    private Activity oThis;
    private Button btn_pay;
    private XlProgressDialog dialog;
    private XlTitle title;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    SharedPreferencesManager.getIntance(oThis).setRegisterInfo("userid"+"123",true);

                    oThis.finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        inevent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog!=null){
            dialog.dismiss();
        }
    }

    private void init(){
        oThis = this;

        btn_pay = (Button) findViewById(R.id.register_btn_pay);
        title = (XlTitle) findViewById(R.id.register_title);
    }

    private void inevent(){

        title.setText(getResources().getString(R.string.zhifu));
        title.setleftText(R.string.fanhui);
        title.setleftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oThis.finish();
                oThis.overridePendingTransition(R.anim.fragment_slide_in_from_left,R.anim.fragment_slide_out_from_right);
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*dialog = new XlProgressDialog(oThis,true,getResources().getString(R.string.qingshaodeng));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                         Message msg = Message.obtain();
                         msg.what=1;
                         handler.sendMessage(msg);
                    }
                },3000);*/
                final SweetAlertDialog sd = new SweetAlertDialog(oThis);
                sd.setCancelable(true);
                //sd.setCanceledOnTouchOutside(true);
                sd.setTitleText(getResources().getString(R.string.zhifuchenggong));
                sd.show();
                sd.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        SharedPreferencesManager.getIntance(oThis).setRegisterInfo("userid"+"123",true);
                        sweetAlertDialog.dismiss();
                        oThis.finish();
                    }
                });
            }
        });
    }




}
