package PNoKio.Server.exception;

public class DuplicateItemName extends RuntimeException{
    public DuplicateItemName() {
        super();
    }

    public DuplicateItemName(String message) {
        super(message);
    }

    public DuplicateItemName(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateItemName(Throwable cause) {
        super(cause);
    }

    protected DuplicateItemName(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
