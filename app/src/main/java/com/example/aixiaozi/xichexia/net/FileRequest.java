package com.example.aixiaozi.xichexia.net;

import android.support.v4.util.ArrayMap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import java.io.File;

/**
 * Created by sy
 * on 2017/9/30.
 * <p>文件操作</p>
 */
public class FileRequest<T> {

    /**
     * 上传头像 不要在主线程调用
     */
    public void requestUpLoad(Object tag,
                              String url,
                              ArrayMap<String,String> params,
                              File file, Class<T> cls,
                              final RequestCallback<T> callback){

        PostRequest<T> request = OkGo.<T>post(url).tag(tag);
        if (params != null) {
            int size = params.size();
            for (int i = 0; i < size; i++) {
                request.params(params.keyAt(i), params.valueAt(i));
            }
        }
        request.params("file",file).execute(new JsonCallback<T>(cls) {
            @Override
            public void onSuccess(Response<T> response) {
                callback.onDone(response.body());
            }

            @Override
            public void onError(Response<T> response) {
                super.onError(response);
                callback.onErr(response.body());
            }

        });
    }


    /**
     * 下载文件
     */
    public void requestDownload(Object tag, String url, final FileDownLoadListener listener){
        OkGo.<File>get(url)
                .tag(tag)
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(Response<File> response) {
                        listener.onDone(response.body());
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        listener.onErr();
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);//这个回调在主线程,可以直接更新UI
                        listener.onProgress(progress);
                    }
                });
    }

    private interface FileDownLoadListener{
        void onDone(File file);
        void onErr();
        void onProgress(Progress progress);
    }

}
