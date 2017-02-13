package com.mm.kyys.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.mapapi.map.Text;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.mm.kyys.Activity.LoginActivity;
import com.mm.kyys.Activity.NoticActivity;
import com.mm.kyys.Model.User;
import com.mm.kyys.MyHelper;
import com.mm.kyys.R;
import com.mm.kyys.Util.MyUtil;
import com.mm.kyys.Util.SharedPreferencesManager;
import com.mm.kyys.View.CircularImage;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.math.BigDecimal;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by sexyXu on 2016/10/25.
 */

public class MineFragment extends BaseFragment {


    private Button btn_logout;
    private TextView tv_dot,tv_message;
    private CircularImage ci_user_photo;
    private TextView tv_user_name;
    private TextView tv_user_iden;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState)
        View view = inflater.inflate(R.layout.fragment_mine,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        event();

    }

    @Override
    public void onResume() {
        super.onResume();
        fresh();
    }

    private void init(){
        btn_logout = (Button) getView().findViewById(R.id.mine_btn_logout);
        tv_message = (TextView) getView().findViewById(R.id.mine_btn_message);

        tv_dot = (TextView) getView().findViewById(R.id.mine_tv_dot);

        ci_user_photo = (CircularImage) getView().findViewById(R.id.mine_user_photo);
        tv_user_name = (TextView) getView().findViewById(R.id.mine_user_name);
        tv_user_iden = (TextView) getView().findViewById(R.id.mine_user_iden);

    }

    private void event(){

        /*if (SharedPreferencesManager.getIntance(getActivity()).getUserInfo(getActivity()).getIdentity()!=2){
            tv_message.setVisibility(View.VISIBLE);
        }else{
            tv_message.setVisibility(View.GONE);
        }*/

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        tv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtil.getIntance().ToActivity(getActivity(), NoticActivity.class,true,null);
                getActivity().findViewById(R.id.mainactivity_dot).setVisibility(View.GONE);
            }
        });
        User user = SharedPreferencesManager.getIntance(getContext()).getUserInfo(getActivity());
        ImageLoader.getInstance().displayImage(user.getPhoto(),ci_user_photo);
        tv_user_name.setText(user.getName());
        switch (user.getIdentity()){
            case 0:
                tv_user_iden.setText(getResources().getString(R.string.guanliyuan));
                break;
            case 1:
                tv_user_iden.setText(getResources().getString(R.string.yisheng));
                break;
            case 2:
                tv_user_iden.setText(getResources().getString(R.string.putongyonghu));
                break;
        }

    }

    private void Logout(){
        SharedPreferencesManager.getIntance(getContext()).setRegisterInfo("userid"+"123",false);
        SharedPreferencesManager.getIntance(getContext()).setUserHasLogin(false);
        EMClient.getInstance().logout(true);
        getActivity().finish();
        MyUtil.getIntance().ToActivity(getActivity(), LoginActivity.class,false,null);
    }

    public void fresh(){
        if (MyHelper.getInstance().hasUnreadMessage()){
            tv_dot.setVisibility(View.VISIBLE);
        }else{
            tv_dot.setVisibility(View.GONE);
        }

    }

}
