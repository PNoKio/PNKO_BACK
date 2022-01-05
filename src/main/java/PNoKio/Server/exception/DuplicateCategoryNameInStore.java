package PNoKio.Server.exception;

public class DuplicateCategoryNameInStore extends RuntimeException{
    public DuplicateCategoryNameInStore() {
        super();
    }

    public DuplicateCategoryNameInStore(String message) {
        super(message);
    }

    public DuplicateCategoryNameInStore(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCategoryNameInStore(Throwable cause) {
        super(cause);
    }

    protected DuplicateCategoryNameInStore(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
