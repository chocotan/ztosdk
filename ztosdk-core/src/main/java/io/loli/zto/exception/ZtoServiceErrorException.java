package io.loli.zto.exception;

public class ZtoServiceErrorException extends ZtoException {
    public ZtoServiceErrorException(int httpStatusCode, String errorCode, String errorMsg, String serverResponseStr) {
        super(httpStatusCode, errorCode, errorMsg, serverResponseStr);
    }
}
