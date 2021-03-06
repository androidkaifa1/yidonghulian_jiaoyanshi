package com.example.mloong.yidonghulian.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import static android.support.constraint.Constraints.TAG;

public class DesityUtils {
    public static int getWidth(Context context) {
        int width = 0;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);

        Log.i(TAG, "屏幕宽度：" + metrics.widthPixels + "px");
        return metrics.widthPixels;
    }
}
