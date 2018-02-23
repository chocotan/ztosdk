package io.loli.zto.exception;

public class ZtoInvalidMsgtypeException extends ZtoException {
    public ZtoInvalidMsgtypeException(int httpStatusCode, String errorCode, String message, String serverResponseStr) {
        super(httpStatusCode, errorCode, message, serverResponseStr);
    }
}
