package com.example.aixiaozi.xichexia.bean;

import android.text.TextUtils;

/**
 * Created by sy
 * on 2017/10/9.
 * <p></p>
 */

public class BaseBean {

    private String Code;
    private String Message;
    private String code;
    private String message;

    public BaseBean() {
    }

    public String getCode() {
        return TextUtils.isEmpty(Code) ? code : Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return TextUtils.isEmpty(Message) ? message : Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
