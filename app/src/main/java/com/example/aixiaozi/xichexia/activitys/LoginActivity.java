package com.example.aixiaozi.xichexia.activitys;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aixiaozi.xichexia.R;
import com.example.aixiaozi.xichexia.base.BaseActivity;
import com.example.aixiaozi.xichexia.base.SToolBar;
import com.example.aixiaozi.xichexia.params.Elements;
import com.example.aixiaozi.xichexia.params.ShowActivity;
import com.example.aixiaozi.xichexia.utils.LogUtil;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements SToolBar {
    private EditText userPhoneV;
    private EditText userPasswordV;
    private TextView forgetPasswordBtn;
    private TextView rigestBtn;
    private Button loginBtn;
    private Tencent tencent;
    private static final String QQ = "qzone";
    private static final String SINA = "sina";
    private BaseUiListener baseUiListener;

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }*/

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        initTitle(getString(R.string.login));
        userPhoneV = getView(R.id.userPhoneV);
        userPasswordV = getView(R.id.userPasswordV);
        bindViewWithClick(R.id.clearPassword, true);
        bindViewWithClick(R.id.clearPhone, true);
        forgetPasswordBtn = bindViewWithClick(R.id.forgetPasswordBtn, true);
        rigestBtn = bindViewWithClick(R.id.rigestBtn, true);
        loginBtn = bindViewWithClick(R.id.loginBtn, true);
        bindViewWithClick(R.id.iv_weixin, true);
        bindViewWithClick(R.id.iv_qq, true);
        bindViewWithClick(R.id.iv_xinlang, true);
    }

    @Override
    public void initData() {
// Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
// 其中APP_ID是分配给第三方应用的appid，类型为String。
        tencent = Tencent.createInstance(Elements.APP_ID, this.getApplicationContext());
// 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
        baseUiListener = new BaseUiListener();
    }

    @Override
    public void addListener() {

    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.clearPassword:
                if (!TextUtils.isEmpty(userPasswordV.getText().toString())) {
                    userPasswordV.getText().clear();
                }
                break;
            case R.id.clearPhone:
                if (!TextUtils.isEmpty(userPhoneV.getText().toString())) {
                    userPhoneV.getText().clear();
                }
                break;
            case R.id.forgetPasswordBtn:
                if (TextUtils.isEmpty(userPhoneV.getText().toString().trim())) {
                    showToast(getString(R.string.inutp_phone_number_tip));
                    return;
                }
                ShowActivity.skipFindPassWordActivity(this, userPhoneV.getText().toString());
                finish();
                break;
            case R.id.rigestBtn:
                ShowActivity.showActivity(this, RegisterActivity.class);
                finish();
                break;
            case R.id.loginBtn:
                showToast("点击了登录按钮");
                break;
            case R.id.iv_weixin:

                break;
            case R.id.iv_qq:
                login();
                break;
            case R.id.iv_xinlang:

                break;
        }
    }

    @Override
    protected boolean enableAutoHideSoftKeyBoard() {
        return true;
    }
    public void login()
    {
        tencent = Tencent.createInstance(Elements.APP_ID, this.getApplicationContext());
        if (!tencent.isSessionValid())
        {
            tencent.login(this, "all", baseUiListener);
        }else{
            tencent.logout(this);
            tencent.login(this,"all",baseUiListener);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, baseUiListener);
    }

    private class BaseUiListener implements IUiListener {

        void doComplete(JSONObject values) {
            try {
                if (values.getInt("ret") == 0) {
                    String token = values.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                    String openID = values.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                    showToast("token:" + token + "   openID    " + openID);
//                    Loging_Group(openID,token,QQ.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onComplete(Object o) {
            // TODO Auto-generated method stub
            if (null == o) {
                LogUtil.e("返回为空登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) o;
            if (jsonResponse.length() == 0) {
                LogUtil.e("返回为空登录失败");
                return;
            }
            doComplete(jsonResponse);
        }

        @Override
        public void onError(UiError e) {
            showToast("onError:" + "code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
        }

        @Override
        public void onCancel() {
            showToast("取消登录...");
        }
    }
    /*调用后台的第三封登录接口*/
//    public void Loging_Group(String open_id,String token,String api_type){
//        WebBase.qq_sina_login(open_id,token,api_type, new JSONHandler(true,context,"正在登录...") {
//            @Override
//            public void onSuccess(JSONObject obj) {
//                JSONObject user=obj.optJSONObject("user");
//                User userbean=new User();
//                userbean.setAvatar(user.optString("avatar"));
//                userbean.setUsername(user.optString("username"));
//                userbean.setUser_id(user.optString("user_id"));
//                userbean.setTruename(user.optString("truename"));
//                userbean.setAuth_token(obj.optString("auth_token"));
//                userbean.setRole(user.optString("role"));
//                userbean.setNickname(user.optString("nickname"));
//                userbean.setPhone(user.optString("phone"));
//                userbean.setIdcard(user.optString("idcard"));
//                dbManager.addUser(userbean);
//                dbManager.closeDB();
//                Intent intent = new Intent(Constants.NEW_LIVE_MSG_READ);
//                context.sendBroadcast(intent);
//                Intent intent2 = new Intent(Constants.UPDATE_VIDEO_LIST);
//                context.sendBroadcast(intent2);
//                Intent intent3 = new Intent(Constants.UP_DATA_MESSAGE);
//                context.sendBroadcast(intent3);
//                Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();
//                if(ActivityUtil.isActivityExist(LoginActivity.class)){
//                    LoginActivity activity= ActivityUtil.getActivity(LoginActivity.class);
//                    if(activity!=null){
//                        activity.finish();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(String err_msg) {
//                showToast(err_msg);
//            }
//        });
//    }
}
