package com.example.aixiaozi.xichexia.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aixiaozi.xichexia.R;
import com.example.aixiaozi.xichexia.listener.OnceClickListener;

import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseActivity extends AppCompatActivity {
    private boolean enableAutoHide;
    private Bundle savedInstanceState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(getLayoutID());//在super之前执行,否则SToolbar标记的activity会找不到id
        this.savedInstanceState=savedInstanceState;
        super.onCreate(savedInstanceState);
//        View view = getView(R.id.action_bar_layout);
//        int barHeight = GetViewHeightUtil.getStatusBarHeight(this);
//        Log.i("--TAG","--- stateNarHeight  ---"+barHeight);
//        ViewGroup.LayoutParams lp = view.getLayoutParams();
//        lp.height=barHeight;
//        lp.width= ViewGroup.LayoutParams.MATCH_PARENT;
//        view.setLayoutParams(lp);
//        setContentView(R.layout.activity_base);
        enableAutoHide = enableAutoHideSoftKeyBoard();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        goBack();
        initView();
        initData();
        addListener();
    }
    public void goBack(){
        getView(R.id.backBtn).setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                finish();
            }
        });
    }
    public void initTitle(String title){
        TextView incTitle = getView(R.id.inc_title);
        if (incTitle!=null){
            incTitle.setText(title);
        }
    }
    public Bundle getSaveBundle(){
        return savedInstanceState;
    }
    //设置初始化布局文件
    public abstract int getLayoutID();
    public abstract void initView();
    public abstract void initData();
    public abstract void addListener();
    //给控件绑定点击事件
    @SuppressWarnings("unchecked")
    public <T extends View>T bindViewWithClick(int resID,boolean isBindClick){
        View view = getView(resID);
        if (isBindClick){
            view.setOnClickListener(clickListener);
        }
        return (T)view;
    }
    //获取布局控件
    @SuppressWarnings("unchecked")
    protected <T extends View>T getView(int resourcesId){
        try {
            return (T) findViewById(resourcesId);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 点击事件
     */
    protected abstract void onViewClick(View view);
    /**
     * 是否点击EditText外部自动隐藏软键盘
     */
    protected abstract boolean enableAutoHideSoftKeyBoard();
    private OnceClickListener clickListener = new OnceClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            onViewClick(v);
        }
    };
    /**
     * create by sy
     * 点击空白处收回软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (enableAutoHide){
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                // 获得当前得到焦点的View
                View v = getCurrentFocus();
                if (isShouldHideInput(v, ev)) {
                    hideSoftInput(v.getWindowToken());
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    public void showToast(String tip){
        if (!TextUtils.isEmpty(tip)){
            Toast.makeText(this,tip,Toast.LENGTH_SHORT).show();
        }
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 1、获取main在窗体的可视区域
     * 2、获取main在窗体的不可视区域高度
     * 3、判断不可视区域高度
     *   ①大于180：键盘显示  获取Scroll的窗体坐标，算出main需要滚动的高度，使scroll显示。
     *   ②小于180：键盘隐藏
     *   该值根据屏幕可以做出修改，在大屏手机上可以适当的调大，不然会出现问题。
     *
     * @param main   根布局
     * @param scroll 需要显示的最下方View
     *
     * http://www.jianshu.com/p/3d946762b878
     * create by sy on 2017/8/31
     *  需要在View初始化之后调用
     *
     * <p>软键盘挡住控件的问题</p>
     */
    public void addLayoutListener(final View main, final View scroll,final View hidden) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                main.getWindowVisibleDisplayFrame(rect);
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                if (mainInvisibleHeight > 180) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    int scrollHeight = (location[1] + scroll.getHeight() + ((RelativeLayout.LayoutParams)
                            scroll.getLayoutParams()).bottomMargin) - rect.bottom;
                    if (scrollHeight > 0) {
                        main.scrollTo(0, scrollHeight);
                    }
                    if (hidden != null)
                        hidden.setVisibility(View.INVISIBLE);
                } else {
                    main.scrollTo(0, 0);
                    if (hidden != null)
                        hidden.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
