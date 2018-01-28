package io.loli.zto.client;

import com.alibaba.fastjson.JSON;
import io.loli.zto.ZtoClientProperties;
import io.loli.zto.dto.*;
import io.loli.zto.exception.*;
import io.loli.zto.util.OkHttpClientUtil;
import io.loli.zto.util.ZtoDigestUtil;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import javaslang.control.Try;
import lombok.extern.java.Log;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 中通开放平台客户端，请参阅具体方法说明
 */
@Log
public class ZtoApiClient {
    private ZtoClientProperties ztoClientProperties;
    private OkHttpClientUtil httpClientUtil;
    public static final Function<Object, String> DEFAULT_JSON_SERIALIZER = obj -> {
        if (obj instanceof String) {
            return (String) obj;
        } else {
            return JSON.toJSONString(obj);
        }
    };

    public ZtoApiClient(ZtoClientProperties ztoClientProperties) {
        this.ztoClientProperties = ztoClientProperties;
        this.httpClientUtil = new OkHttpClientUtil(ztoClientProperties);
    }

    private ZtoApi ztoApi = new ZtoApi();

    private ZtoService ztoService = new ZtoService();
    private ZtoMsgtype ztoMsgtype = new ZtoMsgtype();


    public ZtoApi api() {
        return ztoApi;
    }

    public ZtoMsgtype msgtype() {
        return ztoMsgtype;
    }

    public ZtoService service() {
        return ztoService;
    }

    public class ZtoApi {
        public Observable<Try<ZtoTraceResponse>> traceInterfaceNewTraces(String[] billCodes) {
            return post("traceInterfaceNewTraces", billCodes, ZtoTraceResponse.class);
        }

        public Observable<Try<ZtoTraceResponse>> traceNewest(String[] billCodes) {
            return post("traceInterfaceLatest", billCodes, ZtoTraceResponse.class);
        }

        public Observable<Try<ZtoOpenOrderResponse>> openOrderCreate(ZtoOpenOrderRequest request) {
            return post("OpenOrderCreate", request, ZtoOpenOrderResponse.class);
        }


        public <T> Observable<Try<T>> post(final String api, final Object requestObject,
                                           Class<T> responseClass) {
            return post(api, requestObject, DEFAULT_JSON_SERIALIZER, resp -> JSON.parseObject(resp, responseClass));
        }

        public <S, T> Observable<Try<T>> post(final String api, final S requestObject,
                                              final Function<S, ?> serializer,
                                              final Function<String, T> deserializer) {
            Consumer<ZtoRequest.ZtoRequestBuilder> buildUrl = b -> b.url(cleanUrl(ztoClientProperties.getUrl()) + api);
            Consumer<ZtoRequest.ZtoRequestBuilder> buildHeadersAndParams = builder -> {
                Object requestBody = serializer.apply(requestObject);
                builder.headers(buildHeaderMap(requestBody));
                if (requestBody instanceof String) {
                    builder.jsonBody((String) requestBody);
                } else {
                    builder.formBody((Map<String, String>) requestBody);
                }
            };
            return ZtoApiClient.this.post(buildUrl, buildHeadersAndParams, deserializer);
        }
    }


    public class ZtoService {
        public Observable<Try<ZtoTraceResponse>> traceInterfaceNewTraces(String[] billCodes) {
            return post("traceInterface", "NEW_TRACES", billCodes, ZtoTraceResponse.class);
        }

        public Observable<Try<ZtoTraceResponse>> traceInterfaceLatest(String[] billCodes) {
            return post("traceInterface", "LATEST", billCodes, ZtoTraceResponse.class);
        }

        public Observable<Try<ZtoCommonOrderResponse>> commonOrderCreate(ZtoCommonOrderRequest order) {
            return post("commonOrder", "CRATE", order, ZtoCommonOrderResponse.class);
        }


        public <T> Observable<Try<T>> post(final String service, final String msgType, final Object requestObject,
                                           Class<T> responseClass) {
            return post(service, msgType, requestObject, DEFAULT_JSON_SERIALIZER, resp -> JSON.parseObject(resp, responseClass));

        }

        public <S, T> Observable<Try<T>> post(final String service, final String msgType, final S requestObject,
                                              final Function<S, ?> serializer,
                                              final Function<String, T> deserializer) {
            Consumer<ZtoRequest.ZtoRequestBuilder> buildUrl = b -> b.url(cleanUrl(ztoClientProperties.getUrl()) + "zto/api_utf8/" + service);
            Consumer<ZtoRequest.ZtoRequestBuilder> buildHeadersAndParams = builder -> {
                String requestBody = (String) serializer.apply(requestObject);
                builder.formBody(buildDeprecatedParamMap(msgType, requestBody));
            };
            return ZtoApiClient.this.post(buildUrl, buildHeadersAndParams, deserializer);
        }
    }

    public class ZtoMsgtype {

        public Observable<Try<ZtoEBillOrderResponse>> partnerInsertSubmitAgent(ZtoEBillOrderRequest order) {
            return post("PARTNERINSERT_SUBMITAGENT", order, ZtoEBillOrderResponse.class);
        }

        public Observable<Try<ZtoTraceResponse>> traceInterfaceNewTraces(String[] billCodes) {
            return post("TRACEINTERFACE_NEW_TRACES", billCodes, ZtoTraceResponse.class);
        }

        public Observable<Try<ZtoBagAddrMarkResponse>> bagAddrMark(ZtoBagAddrMarkRequest address) {
            return post("BAGADDRMARK_GETMARK", address, ZtoBagAddrMarkResponse.class);
        }


        public Observable<Try<ZtoTraceResponse>> traceInterfaceLatest(String[] billCodes) {
            return post("TRACEINTERFACE_LATEST", billCodes, ZtoTraceResponse.class);
        }

        public Observable<Try<ZtoCommonOrderResponse>> commonOrderCreate(ZtoCommonOrderRequest order) {
            return post("COMMONORDER_CRATE", order, ZtoCommonOrderResponse.class);
        }

        public <T> Observable<Try<T>> post(final String msgType, final Object requestObject,
                                           Class<T> responseClass) {
            return post(msgType, requestObject, DEFAULT_JSON_SERIALIZER, resp -> JSON.parseObject(resp, responseClass));
        }

        public <S, T> Observable<Try<T>> post(final String msgType, final S requestObject,
                                              final Function<S, ?> serializer,
                                              final Function<String, T> deserializer) {
            Consumer<ZtoRequest.ZtoRequestBuilder> buildUrl = b -> b.url(cleanUrl(ztoClientProperties.getUrl()) + "gateway.do");
            Consumer<ZtoRequest.ZtoRequestBuilder> buildHeadersAndParams = builder -> {
                String requestBody = (String) serializer.apply(requestObject);
                builder.formBody(buildDeprecatedParamMap(msgType, requestBody));
            };
            return ZtoApiClient.this.post(buildUrl, buildHeadersAndParams, deserializer);
        }
    }


    // 给url最后加上 /
    private String cleanUrl(String url) {
        if (!url.endsWith("/")) {
            return url + "/";
        }
        return url;
    }

    private Map<String, String> buildDeprecatedParamMap(String msgType, String data) {
        Map<String, String> params = new HashMap<>();
        params.put("company_id", ztoClientProperties.getCompanyId());
        params.put("msg_type", msgType);
        params.put("data", data);
        String dataDigest = ZtoDigestUtil.md5WithBase64(data + ztoClientProperties.getKey(), "UTF-8");
        params.put("data_digest", dataDigest);
        return params;
    }


    private Map<String, String> buildHeaderMap(Object param) {
        Map<String, String> map = new HashMap<>();
        map.put("x-companyid", ztoClientProperties.getCompanyId());
        String strToDigest = null;
        if (param instanceof String) {
            strToDigest = (String) param;
        } else {
            Map<String, String> paramMap = (Map<String, String>) param;
            strToDigest = paramMap.entrySet().stream().map(d -> d.getKey() + "," + d.getValue()).collect(Collectors.joining("&"));
        }
        map.put("x-datadigest", ZtoDigestUtil.md5WithBase64(strToDigest + ztoClientProperties.getKey(), "UTF-8"));
        return map;
    }


    private <S, T> Observable<Try<T>> post(
            final Consumer<ZtoRequest.ZtoRequestBuilder> buildUrl,
            final Consumer<ZtoRequest.ZtoRequestBuilder> buildHeadersAndParams,
            final Function<String, T> deserializer) {
        return Observable.just(ZtoRequest.builder())
                .doOnNext(buildUrl)
                .doOnNext(buildHeadersAndParams)
                .map(ZtoRequest.ZtoRequestBuilder::build)
                .map(ZtoRequest::toOkHttpRequest)
                .flatMap(req -> Observable.<Response>create(em -> {
                    httpClientUtil.post(req, em);
                }))
                .map(response -> Try.of(() -> dealApiResponse(response)))
                .map(t -> t.mapTry(deserializer::apply));
    }

    private static String dealApiResponse(Response response) {
        int code = response.code();

        String responseStr;
        try {
            responseStr = response.body().string();
        } catch (IOException e) {
            throw new ZtoConnectionException(e);
        }
        if (code >= 400) {
            throw new ZtoGatewayNotAvailableException(code, null, "Zto gateway is not available, statusCode=" + code, responseStr);
        }

        if (responseStr != null) {
            ZtoResponseDto respDto = null;
            try {
                respDto = JSON.parseObject(responseStr, ZtoResponseDto.class);
            } catch (Exception ignored) {
                // 转换失败表示json格式不符合中通网关的错误格式，直接忽略
            }
            if (respDto != null) {
                String statusCode = respDto.getStatusCode();
                String message = respDto.getMessage();
                if (respDto.getStatus() != null && !respDto.getStatus() && respDto.getStatusCode() != null) {
                    switch (respDto.getStatusCode()) {
                        case "S210":
                            throw new ZtoNoPermissionException(code, statusCode, message, responseStr);
                        case "S208":
                            throw new ZtoMissingParameterException(code, statusCode, message, responseStr);
                        case "S211":
                            throw new ZtoDigestErrorException(code, statusCode, message, responseStr);
                        case "S203":
                            throw new ZtoServiceUnAvailableException(code, statusCode, message, responseStr);
                        case "S200":
                            throw new ZtoServiceTimeoutException(code, statusCode, message, responseStr);
                        case "S202":
                            throw new ZtoServiceErrorException(code, statusCode, message, responseStr);
                        case "S207":
                            throw new ZtoApiNotExistException(code, statusCode, message, responseStr);
                        case "G500":
                            throw new ZtoGatewayErrorException(code, statusCode, message, responseStr);
                        case "S11":
                            throw new ZtoInvalidMsgtypeException(code, statusCode, message, responseStr);
                        case "S07":
                            throw new ZtoServiceErrorException(code, statusCode, message, responseStr);
                        case "S02":
                            throw new ZtoDigestErrorException(code, statusCode, message, responseStr);
                    }

                }
            }
        }
        return responseStr;
    }
}
