package com.mm.kyys.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.mm.kyys.Fragment.ChatFragment;
import com.mm.kyys.R;
import com.mm.kyys.Util.MyUtil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;


/**
 * Created by 27740 on 2017/1/6.
 */

public class ChatActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);



        //使用easei的聊天界面
        ChatFragment chatFragment = new ChatFragment();
        //将参数传递给聊天界面
        chatFragment.setArguments(getIntent().getExtras());
        //加载easeui封装的聊天界面fragment
        getSupportFragmentManager().beginTransaction().add(R.id.chat_layout_frame,chatFragment) .commit();

    }


}
