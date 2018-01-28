package io.loli.zto.util;

import io.loli.zto.ZtoClientProperties;
import io.reactivex.ObservableEmitter;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.reactivestreams.Subscriber;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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
                }
            });
        } else {
            try {
                Response response = call.execute();
                callback.onNext(response);
            } catch (Throwable e) {
                callback.onError(e);
            }
        }
    }
}
