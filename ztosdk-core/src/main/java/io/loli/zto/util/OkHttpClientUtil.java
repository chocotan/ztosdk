package io.loli.zto.util;

import okhttp3.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * HttpClient工具类
 */
public class OkHttpClientUtil {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient client;

    public OkHttpClientUtil(Long timeout) {
        this.client = new OkHttpClient.Builder().connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .build();
    }


    public Response post(String url, Map<String, String> formParams) throws IOException {
        return post(url, Collections.emptyMap(), formParams);
    }


    public Response post(String url, Map<String, String> headers, Map<String, String> formParams) throws IOException {
        // 为了防止null值带了的奇怪的问题，所有null的值都被替换为空字符串
        FormBody.Builder builder = new FormBody.Builder();
        formParams.entrySet().forEach(entry -> {
            if (entry.getValue() == null) {
                entry.setValue("");
            }
            builder.add(entry.getKey(), entry.getValue());
        });

        Request.Builder requestBuilder = new Request.Builder().url(url);
        headers.forEach(requestBuilder::header);
        Request request = requestBuilder
                .post(builder.build()).build();
        return client.newCall(request).execute();
    }

    public Response postJson(String url, String jsonStr) throws IOException {
        return postJson(url, Collections.emptyMap(), jsonStr);
    }

    public Response postJson(String url, Map<String, String> headers, String jsonStr) throws IOException {
        RequestBody body = RequestBody.create(JSON, jsonStr);
        Request.Builder requestBuilder = new Request.Builder();
        headers.forEach(requestBuilder::header);
        Request request = requestBuilder.url(url)
                .post(body).build();
        return client.newCall(request).execute();
    }

    public String get(String url) throws IOException {
        Request request = new Request.Builder().url(url)
                .header("Accept-Language", "zh-CN")
                .get().build();
        String string;
        Response response = client.newCall(request).execute();
        string = response.body().string();
        string = string.trim();
        return string;
    }
}
