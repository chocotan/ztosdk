package io.loli.zto.exception;

public class ZtoGatewayErrorException extends ZtoException {

    public ZtoGatewayErrorException(int httpStatusCode, String errorCode, String message, String serverResponseStr) {
        super(httpStatusCode, errorCode, message, serverResponseStr);
    }
}
