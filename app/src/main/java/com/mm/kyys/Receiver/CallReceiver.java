/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mm.kyys.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.mm.kyys.Activity.VideoOrVoiceActivity;
import com.mm.kyys.MyHelper;
import com.mm.kyys.Util.MyUtil;

public class CallReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		if(!MyHelper.getInstance().isLoggedIn())
		    return;
		//username
		String from = intent.getStringExtra("from");
		Log.e("xl", "广播接收："+from);
		//call type
		String type = intent.getStringExtra("type");
		Log.e("xl", "接听类型："+type);
		if("video".equals(type)){ //video call
			MyUtil.wakeUpAndUnlock(context);
		    context.startActivity(new Intent(context, VideoOrVoiceActivity.class)
                    .putExtra("toUserid", from)//.putExtra("isComingCall", true)
					.putExtra("state","response")
					.putExtra("type","video")
					.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		}else{ //voice call
			MyUtil.wakeUpAndUnlock(context);
		    context.startActivity(new Intent(context, VideoOrVoiceActivity.class)
					.putExtra("toUserid", from)//.putExtra("isComingCall", true)
					.putExtra("state","response")
					.putExtra("type","voice")
					.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
		}
	}

}
