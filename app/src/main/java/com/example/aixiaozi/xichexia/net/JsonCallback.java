package com.example.aixiaozi.xichexia.net;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by sy
 * on 2017/9/30.
 * <p>自动解析返回</p>
 *
 * <d>
 * 其实吧 本来是可以根据泛型直接获取类型的 不用在构造器里传类型
 * 但是由于我们规定的返回格式 导致这很尴尬
 * 使用反射获取类上泛型的真实类型是有局限性的
 * 只能获取一层继承结构的数据，层次再多，获取到的数据类型就是T，而不是真实类型，Gson也就无法解析
 * 求稳 于是就在构造器里传了
 * </d>
 */

public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Class<T> mCls;
    public JsonCallback(@NonNull Class<T> cls){
        mCls = cls;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) return null;
        return new Gson().fromJson(new JsonReader(body.charStream()),mCls);
    }
}
