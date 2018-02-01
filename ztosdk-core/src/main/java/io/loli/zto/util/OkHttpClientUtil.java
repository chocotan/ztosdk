package io.loli.zto.util;

import io.loli.zto.ZtoClientProperties;
import io.reactivex.ObservableEmitter;
import lombok.extern.java.Log;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * HttpClient工具类
 */
@Log
public class OkHttpClientUtil {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient client;
    private ZtoClientProperties properties;


    public OkHttpClientUtil(ZtoClientProperties properties) {
        this.properties = properties;
        ZtoLoggingInterceptor interceptor = new ZtoLoggingInterceptor();
        long timeout = properties.getTimeout();
        this.client = new OkHttpClient.Builder().connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();
    }


    public void post(Request request, ObservableEmitter<Response> callback) {
        Call call = client.newCall(request);
        if (properties.getAsync()) {
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError(e);

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    callback.onNext(response);
                    callback.onComplete();
                }
            });
        } else {
            try {
                Response response = call.execute();
                callback.onNext(response);
                callback.onComplete();
            } catch (Throwable e) {
                callback.onError(e);
            }
        }
    }
}
