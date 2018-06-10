package com.example.aixiaozi.xichexia.net;

/**
 * Created by sy
 * on 2017/9/30.
 * <p>解析结果回调</p>
 */

public interface RequestCallback<T> {

    void onDone(T t);
    void onErr(T t);

}
