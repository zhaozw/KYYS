package com.mm.kyys.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.mm.kyys.R;
import com.mm.kyys.Util.AllData;
import com.mm.kyys.Util.MyUtil;

/**
 * Created by 27740 on 2017/1/6.
 */

public class NoticActivity extends BaseActivity {

    private Activity oThis;
    private EaseConversationListFragment easeConversationListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        init();
        inevent();

    }

    private void init() {
        oThis = this;

        easeConversationListFragment = new EaseConversationListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.notice_layout_frame,easeConversationListFragment) .commit();

    }

    private void inevent() {
        easeConversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                String username = AllData.getInstance().getUserName();
                String tousername = "";
                String touserNick = "";
                if (username.equals("x")) {
                    tousername = "l";
                    touserNick = "L";
                }else{
                    tousername = "x";
                    touserNick = "X";
                }

                Intent intent = new Intent(oThis, ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,tousername);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                intent.putExtra(EaseConstant.EXTRA_USER_NICK,touserNick);
                startActivity(intent);
            }
        });
    }

}