package io.loli.zto.exception;

import lombok.Getter;

@Getter
public class ZtoException extends RuntimeException {
    private int httpStatusCode;
    private String errorCode;
    private String errorMsg;
    private String serverResponseStr;

    public ZtoException(int httpStatusCode, String errorCode, String errorMsg, String serverResponseStr) {
        super("" +
                "httpStatusCode=" + httpStatusCode +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", serverResponseStr='" + serverResponseStr + '\'');
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.serverResponseStr = serverResponseStr;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "httpStatusCode=" + getHttpStatusCode() +
                ", errorCode='" + getErrorCode() + '\'' +
                ", errorMsg='" + getErrorMsg() + '\'' +
                ", serverResponseStr='" + getServerResponseStr() + '\'' +
                '}';
    }


}
