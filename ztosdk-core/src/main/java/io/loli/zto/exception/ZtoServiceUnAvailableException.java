package io.loli.zto.exception;

public class ZtoServiceUnAvailableException extends ZtoException {
    public ZtoServiceUnAvailableException(int httpStatusCode, String errorCode, String errorMsg, String serverResponseStr) {
        super(httpStatusCode, errorCode, errorMsg, serverResponseStr);
    }
}
