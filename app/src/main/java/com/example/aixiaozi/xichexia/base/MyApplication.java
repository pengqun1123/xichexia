package com.example.aixiaozi.xichexia.base;

import android.app.Application;
import android.app.admin.ConnectEvent;
import android.content.Context;

import com.example.aixiaozi.xichexia.utils.SharedpreferencesUtil;

/**
 * Author: AiXiaoZi
 * Date: on 2018/6/9.
 */

public class MyApplication extends Application {

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SharedpreferencesUtil.getInstance().initSharedUtil(this);
    }
    public Context getInatanceContext(){
        return context;
    }
    private static MyApplication instance=null;

    public static MyApplication getInstance(){
        if (instance==null){
            synchronized (MyApplication.class){
                if (instance==null){
                    instance=new MyApplication();
                }
            }
        }
        return instance;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(base);
    }
}
