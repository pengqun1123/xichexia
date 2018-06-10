package com.example.aixiaozi.xichexia;

import android.view.View;

import com.example.aixiaozi.xichexia.base.BaseActivity;

public class MainActivity extends BaseActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

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
    //是否可自定隐藏软键盘
    @Override
    protected boolean enableAutoHideSoftKeyBoard() {
        return true;
    }
}
