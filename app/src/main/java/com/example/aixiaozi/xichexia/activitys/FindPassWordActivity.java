package com.example.aixiaozi.xichexia.activitys;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.aixiaozi.xichexia.R;
import com.example.aixiaozi.xichexia.base.BaseActivity;

public class FindPassWordActivity extends BaseActivity {
    private EditText userPhoneV, verifyCodeV, userPasswordV;
    private ImageView clearPhone, clearPassword;
    private Button verifyCodeBtn, findPasswordBtn;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pass_word);
    }*/

    @Override
    public int getLayoutID() {
        return R.layout.activity_find_pass_word;
    }

    @Override
    public void initView() {
        initTitle(getString(R.string.find_password));
        userPhoneV = getView(R.id.userPhoneV);
        verifyCodeV = getView(R.id.verifyCodeV);
        userPasswordV = getView(R.id.userPasswordV);
        clearPhone = bindViewWithClick(R.id.clearPhone, true);
        clearPassword = bindViewWithClick(R.id.clearPassword, true);
        verifyCodeBtn = bindViewWithClick(R.id.verifyCodeBtn, true);
        findPasswordBtn = bindViewWithClick(R.id.findPasswordBtn, true);

    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {

    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
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
            case R.id.findPasswordBtn:

                break;
        }
    }

    @Override
    protected boolean enableAutoHideSoftKeyBoard() {
        return true;
    }
}
