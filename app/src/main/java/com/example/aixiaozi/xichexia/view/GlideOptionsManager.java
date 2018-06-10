package com.example.aixiaozi.xichexia.view;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.example.aixiaozi.xichexia.R;

/**
 * Created by xuhao
 * on 2017/7/18.
 */

public class GlideOptionsManager {

    private static GlideOptionsManager instance;

    private RequestOptions mOptions,mBanner;

    private GlideOptionsManager() {

    }

    public static GlideOptionsManager getInstance() {
        if(instance == null) {
            synchronized (GlideOptionsManager.class) {
                if(instance == null) {
                    instance = new GlideOptionsManager();
                }
            }
        }
        return instance;
    }
    //获取圆形图像
    public RequestOptions getRequestOptions() {
        if(mOptions == null) {
            mOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.xichexia)
                    .error(R.drawable.xichexia)
                    .priority(Priority.HIGH)
                    .transform(new GlideCircleTransform());
        }
        return mOptions;
    }
    //获取比赛的圆形图像
    public RequestOptions getRequestOptionsMatch() {
        if(mOptions == null) {
            mOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.tou_xiang)
                    .error(R.drawable.tou_xiang)
                    .priority(Priority.HIGH)
                    .transform(new GlideCircleTransform());
        }
        return mOptions;
    }
    //获取轮播banner图 获取方形图像
    public RequestOptions getBannerOptions() {
        if(mBanner == null) {
            mBanner = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.xichexia)
                    .error(R.drawable.xichexia)
                    .priority(Priority.HIGH)
                    .transform(new GlideRoundTransform());
        }
        return mBanner;
    }

    public RequestOptions getBannerOptions1() {
        if(mBanner == null) {
            mBanner = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.xichexia)
                    .error(R.drawable.xichexia)
                    .priority(Priority.HIGH)
                    .transform(new GlideRoundTransform());
        }
        return mBanner;
    }
}
