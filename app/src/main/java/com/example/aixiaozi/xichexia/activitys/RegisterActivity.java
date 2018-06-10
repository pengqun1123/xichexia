package com.example.aixiaozi.xichexia.activitys;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.aixiaozi.xichexia.R;
import com.example.aixiaozi.xichexia.base.BaseActivity;
import com.example.aixiaozi.xichexia.params.ShowActivity;

public class RegisterActivity extends BaseActivity {

    private EditText userPhoneV, verifyCodeV, userPasswordV;
    private ImageView clearPhone, clearPassword;
    private Button verifyCodeBtn, registerBtn;

    @Override
    public int getLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        initTitle(getString(R.string.register));
        userPhoneV = getView(R.id.userPhoneV);
        verifyCodeV = getView(R.id.verifyCodeV);
        userPasswordV = getView(R.id.userPasswordV);
        clearPhone = bindViewWithClick(R.id.clearPhone, true);
        clearPassword = bindViewWithClick(R.id.clearPassword, true);
        verifyCodeBtn = bindViewWithClick(R.id.verifyCodeBtn, true);
        registerBtn = bindViewWithClick(R.id.registerBtn, true);
        bindViewWithClick(R.id.goToLogin,true);
        bindViewWithClick(R.id.protocol,true);

    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {

    }

    @Override
    protected void onViewClick(View view) {
     switch (view.getId()){
         case R.id.clearPhone:
             if (!TextUtils.isEmpty(userPhoneV.getText().toString())) {
                 userPhoneV.getText().clear();
             }
             break;
         case R.id.clearPassword:
             if (!TextUtils.isEmpty(userPasswordV.getText().toString())) {
                 userPasswordV.getText().clear();
             }
             break;
         case R.id.verifyCodeBtn:
             showToast("点击了获取验证码按钮");
             break;
         case R.id.registerBtn:
             showToast("点击了注册按钮");
             break;
         case R.id.goToLogin:
             ShowActivity.showActivity(this,LoginActivity.class);
             finish();
             break;
         case R.id.protocol:
             showToast("点击了阅读用户协议");
             break;
     }
    }

    @Override
    protected boolean enableAutoHideSoftKeyBoard() {
        return true;
    }
}
