package io.loli.zto.client;

import lombok.Builder;
import lombok.Getter;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.Map;

import static io.loli.zto.util.OkHttpClientUtil.JSON;

@Getter
@Builder
public class ZtoRequest {
    private String url;
    private Map<String, String> headers;
    private String jsonBody;
    private Map<String, String> formBody;


    public Request toOkHttpRequest() {
        if (jsonBody == null) {
            FormBody.Builder builder = new FormBody.Builder();
            if (formBody != null) {
                // 为了防止null值带了的奇怪的问题，所有null的值都被替换为空字符串
                formBody.entrySet().forEach(entry -> {
                    if (entry.getValue() == null) {
                        entry.setValue("");
                    }
                    builder.add(entry.getKey(), entry.getValue());
                });
            }
            Request.Builder requestBuilder = new Request.Builder().url(url);
            if (headers != null)
                headers.forEach(requestBuilder::header);
            return requestBuilder
                    .post(builder.build()).build();
        } else {
            RequestBody body = RequestBody.create(JSON, jsonBody);
            Request.Builder requestBuilder = new Request.Builder();
            if (headers != null)
                headers.forEach(requestBuilder::header);
            return requestBuilder.url(url)
                    .post(body).build();

        }
    }

}
