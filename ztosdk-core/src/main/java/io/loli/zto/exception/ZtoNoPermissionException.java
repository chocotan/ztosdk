package io.loli.zto.exception;

public class ZtoNoPermissionException  extends ZtoException {

    public ZtoNoPermissionException(int httpStatusCode, String errorCode, String message, String serverResponseStr) {
        super(httpStatusCode, errorCode, message, serverResponseStr);
    }
}
