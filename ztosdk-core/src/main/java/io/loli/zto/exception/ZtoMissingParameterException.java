package io.loli.zto.exception;

public class ZtoMissingParameterException extends ZtoException {
    public ZtoMissingParameterException(int httpStatusCode, String errorCode, String errorMsg, String serverResponseStr) {
        super(httpStatusCode, errorCode, errorMsg, serverResponseStr);
    }
}
