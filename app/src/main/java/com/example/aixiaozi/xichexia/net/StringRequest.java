package com.example.aixiaozi.xichexia.net;

import android.support.v4.util.ArrayMap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * Created by sy
 * on 2017/9/30.
 * <p>String请求 </p>
 */

public class StringRequest {

    public void stringRequest(Object tag, String url, ArrayMap<String,String> params,
                              final RequestCallback<String> callback){
        OkGo.<String>post(url)
                .params(params)
                .tag(tag)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        callback.onDone(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callback.onErr(response.body());
                    }
                });
    }

}
