package io.loli.zto.exception;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor

public class ZtoException extends RuntimeException {
    private int httpStatusCode;
    private String errorCode;
    private String errorMsg;
    private String serverResponseStr;

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "httpStatusCode=" + httpStatusCode +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", serverResponseStr='" + serverResponseStr + '\'' +
                '}';
    }
}
