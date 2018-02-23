package io.loli.zto.exception;

public class ZtoServiceTimeoutException extends ZtoException {

    public ZtoServiceTimeoutException(int httpStatusCode, String errorCode, String message, String serverResponseStr) {
        super(httpStatusCode, errorCode, message, serverResponseStr);
    }
}
