package com.mm.kyys.Wighet;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.TextView;

import com.mm.kyys.R;


public class XlProgressDialog extends ProgressDialog {

	private Dialog progressDialog;

	private TextView msg;


	public XlProgressDialog(Context context, boolean cancelable, String msgStr) {
		super(context);

		progressDialog = new Dialog(context, R.style.progress_dialog);
		//progressDialog = new Dialog(context);
		progressDialog.setContentView(R.layout.wait_dialog_d);
		progressDialog.getWindow().setBackgroundDrawableResource(
				android.R.color.transparent);
		progressDialog.setCancelable(cancelable);
		msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);

		if (msgStr.isEmpty()) {
			msg.setText("请稍等...");
		} else {
			msg.setText(msgStr);
		}

		progressDialog.show();

	}

	@Override
	public void dismiss() {
		progressDialog.dismiss();
	}

}