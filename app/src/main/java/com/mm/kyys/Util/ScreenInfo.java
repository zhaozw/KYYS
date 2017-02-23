package com.mm.kyys.Util;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Display;

/**
 * Created by 27740 on 2017/2/22.
 */

public class ScreenInfo {

    private Activity oThis;
    public static ScreenInfo intance;

    public static ScreenInfo getIntance(Activity oThis){
        if (intance == null){
            intance = new ScreenInfo(oThis);
        }
        return intance;
    }

    public ScreenInfo(Activity oThis){
        this.oThis = oThis;
    }

    //获取整个屏幕的大小（包括通知栏）
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR2)
    public int[] getAllScreenSize(){
        int[] arr_all = new int[2];
        Display display = oThis.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        arr_all[0] = point.x;
        arr_all[1] = point.y;
        return arr_all;
    }
    //获取window大小（不包括通知栏）
    public int[] getWindowSize(){
        int[] arr_window = new int[2];
        Rect rect = new Rect();
        oThis.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        arr_window[0] = rect.width();
        arr_window[1] = rect.height();
        return arr_window;
    }
    //获取View区域大小
    public int[] getViewSize(int id){
        int[] arr_view = new int[2];
        Rect rect = new Rect();
        oThis.getWindow().findViewById(id).getDrawingRect(rect);
        arr_view[0] = rect.width();
        arr_view[1] = rect.height();
        return arr_view;
    }

}
