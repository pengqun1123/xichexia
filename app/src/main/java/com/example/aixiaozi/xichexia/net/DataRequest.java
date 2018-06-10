package com.example.aixiaozi.xichexia.net;

import android.support.v4.util.ArrayMap;

import com.example.aixiaozi.xichexia.bean.BaseBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

/**
 * Created by sy
 * on 2017/9/30.
 * <p>网络请求</p>
 * <d>
 *     为什么要加这一层呢,主要是防止说要换一个网络请求框架的情况,那只要改这里就可以了
 * </d>
 * ex:
 *      ArrayMap<String,String> map = new ArrayMap<>();
 *      map.put( {key} , {value} );
 *      new DataRequest<Data>().request( {tag} , {url} , map, Data.class,
 *                    new RequestCallback<Data>() {
 *                          override
 *                          public void onDone(Data data) {
 *                              Log.e("123",data.toString());
 *                          }
 *
 *                          @ override
 *                          public void onErr(Data data) {
 *                              Log.e("123",data.toString());
 *                          }
 */

public class DataRequest<T extends BaseBean> {

    /**
     * 自动解析的请求
     *
     * @param tag  标记 用于取消请求
     * @param url  请求地址
     * @param params  参数array
     * @param cls  转换类型
     * @param callback  回调
     */
    public void request(Object tag, String url, ArrayMap<String,String> params,
                        final Class<T> cls, final RequestCallback<T> callback){
        HttpParams httpParams = new HttpParams();
        int size = params.size();
        for (int i = 0; i < size; i++) {
            httpParams.put(params.keyAt(i),params.valueAt(i));
        }
        OkGo.<T>post(url)
            .tag(tag)
            .params(httpParams)
            .execute(new JsonCallback<T>(cls) {
                @Override
                public void onSuccess(Response<T> response) {
                    try {
                        if ("0".equals(response.body().getCode()))
                            callback.onDone(response.body());
                        else
                            callback.onErr(response.body());
                    }catch (Exception e){
                        e.printStackTrace();
                        onError(response);
                    }
                }

                @Override
                public void onError(Response<T> response) {
                    super.onError(response);
                    if(response.body() != null){
                        callback.onErr(response.body());
                    }else{
                        try {
                            T t = cls.newInstance();
                            t.setCode("-100");
                            t.setMessage("网络错误");
                            callback.onErr(t);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
    }

    public void noParamsRequest(Object tag, String url,final Class<T> cls, final RequestCallback<T> callback){
        OkGo.<T>post(url)
                .tag(tag)
                .execute(new JsonCallback<T>(cls) {
                    @Override
                    public void onSuccess(Response<T> response) {
                        try {
                            if ("0".equals(response.body().getCode()))
                                callback.onDone(response.body());
                            else
                                callback.onErr(response.body());
                        }catch (Exception e){
                            e.printStackTrace();
                            onError(response);
                        }
                    }

                    @Override
                    public void onError(Response<T> response) {
                        super.onError(response);
                        if(response.body() != null){
                            callback.onErr(response.body());
                        }else{
                            try {
                                T t = cls.newInstance();
                                t.setCode("-100");
                                t.setMessage("网络错误");
                                callback.onErr(t);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

}
