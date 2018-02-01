package io.loli.zto.exception;

public class ZtoApiNotExistException extends ZtoException {

    public ZtoApiNotExistException(int httpStatusCode, String errorCode, String message, String serverResponseStr) {
        super(httpStatusCode, errorCode, message, serverResponseStr);
    }
}
