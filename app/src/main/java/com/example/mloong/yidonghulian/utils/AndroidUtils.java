package com.example.mloong.yidonghulian.utils;

import android.content.Context;

public class AndroidUtils {
    /**
     * 将 px单位转换成dp
     *
     * @param paramContext
     * @param paramFloat
     * @return
     */
    public static int dp2px(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
    }

    /**
     * 将dp单位转换为px
     *
     * @param paramContext
     * @param paramFloat
     * @return
     */
    public static int px2dp(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().density);
    }
}
