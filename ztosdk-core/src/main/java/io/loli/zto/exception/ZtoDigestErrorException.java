package io.loli.zto.exception;

public class ZtoDigestErrorException   extends ZtoException{

    public ZtoDigestErrorException(int httpStatusCode, String errorCode, String message, String serverResponseStr) {
        super(httpStatusCode, errorCode, message, serverResponseStr);
    }
}