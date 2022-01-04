package PNoKio.Server.exception;

public class DuplicateStoreAndBranch extends RuntimeException{
    public DuplicateStoreAndBranch() {
        super();
    }

    public DuplicateStoreAndBranch(String message) {
        super(message);
    }

    public DuplicateStoreAndBranch(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateStoreAndBranch(Throwable cause) {
        super(cause);
    }

    protected DuplicateStoreAndBranch(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
