package com.mm.kyys.Wighet;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mm.kyys.R;


public class XlTitle extends LinearLayout {
	private TextView textview;
	private Button button_left, button_right;
	private LinearLayout layout;

	public XlTitle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Init(context);
	}

	public XlTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
		Init(context);
	}

	public XlTitle(Context context) {
		super(context);
		Init(context);
	}

	public void Init(Context context){
		View view = LayoutInflater.from(context).inflate(R.layout.mytitle, this, true);
		button_left = (Button)view.findViewById(R.id.mytitle_button_left);
		
		button_right = (Button)view.findViewById(R.id.mytitle_button_right);
		
		layout = (LinearLayout) view.findViewById(R.id.mytitle_layout);
		
		textview = (TextView)view.findViewById(R.id.mytitle_textview_title);
		
		textview.setClickable(false);
		textview.setFocusable(false);
		
		textview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

			}
		});
		

	}

	public void setText(String title) {
		textview.setText(title);
	}

	public void setLeftVisibility(boolean visibility){
		if (visibility)
			button_left.setVisibility(VISIBLE);
		else
			button_left.setVisibility(INVISIBLE);
	}

	public void setRightVisibility(boolean visibility){
		if (visibility)
			button_right.setVisibility(VISIBLE);
		else
			button_right.setVisibility(INVISIBLE);
	}

	public void setleftClickListener(OnClickListener listener1) {
		button_left.setOnClickListener(listener1);

	}

	public void setrightClickListener(OnClickListener listener2) {
		button_right.setOnClickListener(listener2);
	}

	public void setleftText(int text_id){
		button_left.setText(getResources().getString(text_id));
	}

	public void setrightText(int text_id){
		button_right.setText(getResources().getString(text_id));
	}

	public void setleftbg(int drawable_id){
		button_left.setBackgroundResource(drawable_id);
	}

	public void setrightbg(int drawable_id){
		button_right.setBackgroundResource(drawable_id);
	}

	public void setleftcolor(int color_id){
		button_left.setBackgroundColor(color_id);
	}

	public void setrightcolor(int color_id){
		button_right.setBackgroundColor(color_id);
	}

	public void setlefttextcolor(int text_color){
		button_left.setTextColor(text_color);
	}

	public void setrighttextcolor(int text_color){
		button_right.setTextColor(text_color);
	}

}
