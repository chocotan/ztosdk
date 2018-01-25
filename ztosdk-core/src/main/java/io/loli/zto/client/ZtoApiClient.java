package io.loli.zto.client;

import com.alibaba.fastjson.JSON;
import io.loli.zto.ZtoClientProperties;
import io.loli.zto.exception.*;
import io.loli.zto.util.OkHttpClientUtil;
import io.loli.zto.util.ZtoDigestUtil;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 中通开放平台客户端，请参阅具体方法说明
 */
public class ZtoApiClient {
    private ZtoClientProperties account;
    private OkHttpClientUtil httpClientUtil;

    public ZtoApiClient(ZtoClientProperties account) {
        this.account = account;
        this.httpClientUtil = new OkHttpClientUtil(account.getTimeout());
    }


    /**
     * 调用中通API（json方式），此方法适用于这样的中通url: http://japi.zto.cn/commonOrder<br>
     * 其中，commonOrder即为 apiName
     * <p>
     * 当请求被中通网关拦截并返回异常时(签名错误、未传必须参数等)，会抛出ZtoException的子类
     *
     * @param apiName api名字
     * @param json    请求的json字符串，作为请求body传给中通
     * @return 中通API的返回值
     * @throws ZtoException 当返回值出现异常时，抛出异常
     */
    public String postApi(String apiName, String json) {
        Response response = null;
        String responseStr = null;
        try {
            Map<String, String> map = buildHeaderMap(json);
            response = httpClientUtil.postJson(account.getUrl() + apiName, map, json);
            responseStr = response.body().string();
        } catch (Exception e) {
            throw new ZtoConnectionException(e);
        }
        return dealApiResponse(response, responseStr);
    }


    /**
     * 调用中通API（表单提交方式），此方法适用于这样的中通url: http://japi.zto.cn/commonOrder<br>
     * <p>
     * 其中，commonOrder即为 apiName
     * <p>
     * 当请求被中通网关拦截并返回异常时(签名错误、未传必须参数等)，会抛出ZtoException的子类
     *
     * @param apiName api名字
     * @param params  请求的参数map，作为请求body传给中通
     * @return 中通API的返回值
     * @throws ZtoException 当返回值出现异常时，抛出异常
     */
    public String postApi(String apiName, Map<String, String> params) {
        Response response = null;
        String responseStr = null;
        try {
            Map<String, String> map = buildHeaderMap(params);
            response = httpClientUtil.post(cleanUrl(cleanUrl(account.getUrl())) + apiName, map, params);
            responseStr = response.body().string();
        } catch (Exception e) {
            throw new ZtoConnectionException(e);
        }
        return dealApiResponse(response, responseStr);
    }

    // 给url最后加上 /
    private String cleanUrl(String url) {
        if (!url.endsWith("/")) {
            return url + "/";
        }
        return url;
    }


    /**
     * 调用中通Service（表单提交方式），此方法适用于这样的中通url: http://japi.zto.cn/zto/api_utf8/commonOrder<br>
     * <p>
     * 其中，commonOrder即为 serviceName
     * <p>
     * <p>
     * 当请求被中通网关拦截并返回异常时(签名错误、未传必须参数等)，会抛出ZtoException的子类
     *
     * @param serviceName service名字
     * @param msgType     这个service的msgType，如果文档中没有写msgType，则传null
     * @param data        data参数
     * @return 中通接口的返回值
     * @throws ZtoException 当返回值出现异常时，抛出异常
     * @deprecated 该调用方法已过时
     */
    @Deprecated
    public String postService(String serviceName, String msgType, String data) {
        Response response = null;
        String responseStr = null;
        try {
            Map<String, String> params = buildDeprecatedParamMap(msgType, data);
            response = httpClientUtil.post(cleanUrl(account.getUrl()) + "zto/api_utf8/" + serviceName, params);
            responseStr = response.body().string();
        } catch (Exception e) {
            throw new ZtoConnectionException(e);
        }
        return dealApiResponse(response, responseStr);
    }


    /**
     * 调用中通msgType（表单提交方式），此方法适用于这样的中通url: http://japi.zto.cn/gateway.do<br>
     * <p>
     * 当请求被中通网关拦截并返回异常时(签名错误、未传必须参数等)，会抛出ZtoException的子类
     *
     * @param msgType 这个service的msgType，如果文档中没有写msgType，则传null
     * @param data    data参数
     * @return 中通接口的返回值
     * @throws ZtoException 当返回值出现异常时，抛出异常
     * @deprecated 该调用方法已过时
     */
    @Deprecated
    public String postMsgType(String msgType, String data) {
        Response response = null;
        String responseStr = null;
        try {
            Map<String, String> params = buildDeprecatedParamMap(msgType, data);
            response = httpClientUtil.post(cleanUrl(account.getUrl()) + "gateway.do", params);
            responseStr = response.body().string();
        } catch (Exception e) {
            throw new ZtoConnectionException(e);
        }
        return dealApiResponse(response, responseStr);
    }

    private Map<String, String> buildDeprecatedParamMap(String msgType, String data) {
        Map<String, String> params = new HashMap<>();
        params.put("company_id", account.getCompanyId());
        params.put("msg_type", msgType);
        params.put("data", data);
        String dataDigest = ZtoDigestUtil.md5WithBase64(data + account.getKey(), "UTF-8");
        params.put("data_digest", dataDigest);
        return params;
    }


    private Map<String, String> buildHeaderMap(Object param) {
        Map<String, String> map = new HashMap<>();
        map.put("x-companyid", account.getCompanyId());
        String strToDigest = null;
        if (param instanceof String) {
            strToDigest = (String) param;
        } else {
            Map<String, String> paramMap = (Map<String, String>) param;
            strToDigest = paramMap.entrySet().stream().map(d -> d.getKey() + "," + d.getValue()).collect(Collectors.joining("&"));
        }
        map.put("x-datadigest", ZtoDigestUtil.md5WithBase64(strToDigest + account.getKey(), "UTF-8"));
        return map;
    }

    private String dealApiResponse(Response response, String responseStr) {
        int code = response.code();

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
                if (!respDto.getStatus()) {
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
                    }

                }
            }
        }
        return responseStr;
    }
}
