package io.loli.zto.exception;

public class ZtoConnectionException  extends RuntimeException{
    public ZtoConnectionException(Throwable cause) {
        super(cause);
    }

    public ZtoConnectionException(String message) {
        super(message);
    }

    public ZtoConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
