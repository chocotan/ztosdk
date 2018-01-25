package io.loli.zto.exception;

public class ZtoGatewayNotAvailableException extends ZtoException {

    public ZtoGatewayNotAvailableException(int httpStatusCode, String errorCode, String message, String serverResponseStr) {
        super(httpStatusCode, errorCode, message, serverResponseStr);
    }
}
