package com.mm.kyys.Wighet;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mm.kyys.R;
import com.mm.kyys.View.DrawHookView;


/**
 * Created by 27740 on 2016/12/19.
 */

public class XlDialog extends Dialog {

    private TextView tv_title;
    private DrawHookView dhv;
    private Button btn_ok;

    private XlDialogListener xlDialogListener;

    public interface XlDialogListener{
        void okIsClicked();
        void Dismiss();
    }


    public void setXlDialogListener(XlDialogListener xlDialogListener){
        this.xlDialogListener = xlDialogListener;
    }

    //private View.OnClickListener ok_listener;

    public XlDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    public XlDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected XlDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xldialog);
        //按空白处可以取消动画
        setCanceledOnTouchOutside(true);

        tv_title = (TextView) findViewById(R.id.xldialog_tv_title);
        dhv = (DrawHookView) findViewById(R.id.xldialog_dhv);
        btn_ok = (Button) findViewById(R.id.xldialog_btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xlDialogListener.okIsClicked();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        xlDialogListener.Dismiss();
    }
}
