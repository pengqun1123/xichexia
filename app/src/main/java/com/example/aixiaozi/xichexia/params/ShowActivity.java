package com.example.aixiaozi.xichexia.params;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.aixiaozi.xichexia.activitys.FindPassWordActivity;
import com.example.aixiaozi.xichexia.utils.SharedpreferencesUtil;

/**
 * Author: AiXiaoZi
 * Date: on 2018/6/9.
 */

public class ShowActivity {
    private static Intent intent;
    public static void StartActivity(Context context, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        intent = null;
    }
    public static void StartActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
        if (intent != null) {
            intent = null;
        }
    }
    public static void StartActivityForResult(Activity activity, Intent intent, int code) {
        activity.startActivityForResult(intent, code);
        if (intent != null) {
            intent = null;
        }
    }
    public static void showActivity(Context context, Class<?> c) {
        intent = new Intent(context, c);
        StartActivity(context, intent);
    }
    public static void showActivity(Activity activity, Bundle aBundle, Class<?> c) {
        Intent intent = new Intent(activity, c);
        if (null != aBundle) {
            intent.putExtras(aBundle);
        }
        StartActivity(activity, intent);
    }

    public static void showActivityForResult(Activity activity, Bundle aBundle, Class<?> c, int code) {
        Intent intent = new Intent(activity, c);
        if (null != aBundle) {
            intent.putExtras(aBundle);
        }
        StartActivityForResult(activity, intent, code);
    }
//    @SuppressLint("ShowToast")
//    public static boolean checkLoginStatus(Context context, String msg){
//        if (msg.equals(Constans.INVALID_ERR_CODE)||msg.equals(Constans.INVALID_ERR_MSG)){
//            Toast.makeText(context, context.getString(R.string.user_invalid_login), Toast.LENGTH_SHORT);
//            return true;
//        }else if (msg.equals(Constans.INVALID_LOGIN_CODE)||msg.equals(Constans.INVALID_LOGIN_MSG)){
//            Toast.makeText(context, context.getString(R.string.invalid_login_err), Toast.LENGTH_SHORT);
//            return true;
//        }else {
//            Toast.makeText(context, msg, Toast.LENGTH_SHORT);
//            return false;
//        }
//    }
    /**
     * 是否登录，未登录打开登录页面
     *
     * @param activity
     * @return
     */
    @SuppressLint("ShowToast")
//    public static boolean isLogin(Activity activity,int flag) {
//        SharedpreferencesUtil
//        if (TextUtils.isEmpty(MatchSharedUtil.AuthToken())) {
//            Toast.makeText(activity,activity.getString(R.string.login_tip),Toast.LENGTH_SHORT);
//            intent = new Intent(activity, LoginActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putInt(ParamsKey.FG_FLAG,flag);
//            intent.putExtras(bundle);
//            StartActivity(activity, intent);
//            return false;
//        } else {
//            return true;
//        }
//    }
    //跳转找回密码页面
    public static void skipFindPassWordActivity(Activity activity,String phone) {
        Bundle bundle = new Bundle();
        bundle.putString(IntentKey.PHONE,phone);
        showActivity(activity,bundle, FindPassWordActivity.class);
    }
}
