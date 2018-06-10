package com.example.aixiaozi.xichexia.activitys;

import android.view.View;

import com.bumptech.glide.Glide;
import com.example.aixiaozi.xichexia.R;
import com.example.aixiaozi.xichexia.base.BaseActivity;

public class UserDetailActivity extends BaseActivity {

    @Override
    public int getLayoutID() {
        return R.layout.activity_user_detail;
    }

    @Override
    public void initView() {
        initTitle(getString(R.string.user_detail));
//        Glide.with(mContext).load(result.getTop_players().get(0))
//                .apply(GlideOptionsManager.getInstance()
//                        .getRequestOptionsMatch())
//                .into(holder.imvOneAvatar);
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {

    }

    @Override
    protected void onViewClick(View view) {

    }

    @Override
    protected boolean enableAutoHideSoftKeyBoard() {
        return false;
    }
}
