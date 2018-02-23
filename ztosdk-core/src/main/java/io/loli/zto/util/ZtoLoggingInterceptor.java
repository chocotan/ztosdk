package io.loli.zto.util;

import lombok.extern.java.Log;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static com.alibaba.fastjson.util.IOUtils.UTF8;

@Log
public class ZtoLoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;


        Connection connection = chain.connection();

        Map<String, Object> headerMap = new HashMap<>();
        if (hasRequestBody) {
            // Request body headers are only present when installed as a network interceptor. Force
            // them to be included (when available) so there values are known.
            if (requestBody.contentType() != null) {
                headerMap.put("Content-Type", requestBody.contentType().toString());
            }
            if (requestBody.contentLength() != -1) {
                headerMap.put("Content-Length", requestBody.contentLength());
            }
        }

        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                headerMap.put(name, headers.value(i));
            }
        }
        String requestStr = null;

        if (hasRequestBody) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            requestStr = buffer.readString(charset);
            Object ct = headerMap.get("Content-Type");
            if (ct != null && ct.toString().contains("x-www-form-urlencoded")) {
                try {
                    requestStr = URLDecoder.decode(requestStr, "UTF-8");
                } catch (Exception ignored) {
                }
            }
        }


        long startNs = System.nanoTime();
        Response response;
        long tookMs;
        String responseStr = null;
        int code = -1;
        Throwable exception = null;
        try {
            response = chain.proceed(request);
            ResponseBody responseBody = response.body();
            long contentLength = responseBody != null ? responseBody.contentLength() : 0;
            code = response.code();
            BufferedSource source = null;
            if (responseBody != null) {
                source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                if (contentLength != 0) {
                    responseStr = buffer.clone().readString(charset);
                }
            }
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            if (log.isLoggable(Level.FINE)) {
                StringBuilder sb = new StringBuilder();
                sb.append("url=").append(request.url());
                sb.append(" ,header=").append(headerMap);
                sb.append(" ,request={{").append(requestStr).append("}}");
                sb.append(" ,cost=").append(tookMs);
                sb.append(" ,code=").append(code);
                if (exception != null) {
                    sb.append(" ,exception=").append(ExceptionUtils.getStackTrace(exception));
                } else {
                    sb.append(" ,response=").append(responseStr);
                }
                log.fine(sb.toString());
            }
        }

        return response;
    }
}
