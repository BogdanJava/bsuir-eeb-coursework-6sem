package by.bsuir.eeb.rsoicoursework.exceptions;

public class AccountActionException extends RuntimeException {

    public AccountActionException() {
        super();
    }

    public AccountActionException(String message) {
        super(message);
    }

    public AccountActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountActionException(Throwable cause) {
        super(cause);
    }

    protected AccountActionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
