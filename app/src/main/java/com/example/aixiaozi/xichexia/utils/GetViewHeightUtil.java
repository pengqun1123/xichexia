package com.example.aixiaozi.xichexia.utils;

import android.content.Context;

/**
 * Author: AiXiaoZi
 * Date: on 2018/6/9.
 */

public class GetViewHeightUtil {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
