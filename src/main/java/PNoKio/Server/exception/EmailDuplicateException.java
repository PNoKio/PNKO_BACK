package PNoKio.Server.exception;


import java.security.spec.ECFieldF2m;

public class EmailDuplicateException extends RuntimeException {

    public EmailDuplicateException() {
        super();
    }

    public EmailDuplicateException(String message) {
        super(message);
    }

    public EmailDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailDuplicateException(Throwable cause) {
        super(cause);
    }

    protected EmailDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
