package com.mm.kyys.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mm.kyys.R;

/**
 * Created by 27740 on 2016/12/19.
 */

public class DrawHookView extends View {

    private final String TAG = "drawhookview";

    //绘制圆弧的进度值
    private int progress = 0;
    //线1的x轴
    private int line1_x = 0;
    //线1的y轴
    private int line1_y = 0;
    //线2的x轴
    private int line2_x = 0;
    //线2的y轴
    private int line2_y = 0;
    //选择的View   对号或错号
    private int view_type;
    //画线的速度
    private int speed = 20;

    public DrawHookView(Context context) {
        this(context,null);
    }

    public DrawHookView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawHookView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //默认画对号
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.DrawHookView);
        Log.e("xl", "view_type0="+view_type);
        view_type = ta.getInt(R.styleable.DrawHookView_view,1);
        Log.e("xl", "view_type1="+view_type);
        ta.recycle();
    }

    //绘制

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        progress+=4;

        /**
         * 绘制圆弧
         */
        Paint paint = new Paint();
        //设置画笔颜色
        paint.setColor(getResources().getColor(android.R.color.holo_red_light));
        //设置圆弧的宽度
        paint.setStrokeWidth(5);
        //设置圆弧为空心
        paint.setStyle(Paint.Style.STROKE);
        //消除锯齿
        paint.setAntiAlias(true);

        //获取圆心的x坐标
        int center = getWidth() / 2;
        int center1 = center - getWidth() / 5;
        //圆弧半径
        int radius = getWidth() / 2 - 10;

        //定义的圆弧的形状和大小的界限
        RectF rectF = new RectF(center - radius -1, center - radius -1 ,center + radius + 1, center + radius + 1);

        //根据进度画圆弧
        canvas.drawArc(rectF, 235, -360 * progress / 100, false, paint);
        Log.e("xl", "view_type="+view_type);

        switch (view_type){
            case 0:
                Log.e("xl", "画对号");
                DrawDH(canvas,paint,radius,center,center1);
                break;
            case 1:
                Log.e("xl", "画错号");
                DrawCH(canvas,paint);
                break;
        }
        //每隔10毫秒界面刷新
        postInvalidateDelayed(10);
    }

    private void DrawDH(Canvas canvas,Paint paint,int radius,int center,int center1){
        /**
         *  画对号
         */

        //先等圆弧画完，才话对勾
        if(progress >= 100&&view_type==0) {
            if(line1_x < radius / 3) {
                line1_x+=speed;
                line1_y+=speed;
            }
            //画第一根线
            canvas.drawLine(center1-10, center, center1 + line1_x-10, center + line1_y, paint);

            if (line1_x == radius / 3) {
                line2_x = line1_x;
                line2_y = line1_y;
                Log.i(TAG, "判断第一条线有没有画完");
                line1_x+=speed;
                line1_y+=speed;
            }
            if (line1_x >= radius / 3 && line2_x <= radius) {
                line2_x+=speed;
                line2_y-=speed ;
                Log.i(TAG, "画第二条线");
            }
            //画第二根线

            canvas.drawLine(center1 + line1_x - 1-10, center + line1_y, center1 + line2_x-10, center + line2_y+100, paint);
        }
    }

    private void DrawCH(Canvas canvas,Paint paint){
/**
 *  画错号
 */

        if (progress>=100&&view_type==1){
            if (line1_x == 0){
                line1_x = getWidth()/4;
                line1_y = getWidth()/4;
            }
            if (line1_x < 3*getWidth()/4){
                line1_x+=speed;
                line1_y+=speed;
            }
            //已经画完第一条线
            if (line1_x>=3*getWidth()/4+speed){
                canvas.drawLine(getWidth()/4,getWidth()/4,3*getWidth()/4,3*getWidth()/4,paint);
            }else{
                //没有画完第一条线
                canvas.drawLine(getWidth()/4,getWidth()/4,line1_x,line1_y,paint);
            }

            if (line1_x < 3*getWidth()/4+speed && line1_x >= 3*getWidth()/4){
                line2_x = 3*getWidth()/4;
                line2_y = getWidth()/4;
                line1_x+=speed;
                line1_y+=speed;
            }
            if (line2_x>getWidth()/4){
                line2_x-=speed;
                line2_y+=speed;
            }
            if (line2_x>getWidth()/4-speed && line2_x!=0){
                if (line2_x < getWidth()/4){
                    canvas.drawLine(3*getWidth()/4,getWidth()/4,getWidth()/4,3*getWidth()/4,paint);
                }else{
                    canvas.drawLine(3*getWidth()/4,getWidth()/4,line2_x,line2_y,paint);
                }
            }

        }
    }
}
