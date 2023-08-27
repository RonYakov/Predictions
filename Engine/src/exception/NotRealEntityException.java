package exception;

public class NotRealEntityException extends RuntimeException {
    public NotRealEntityException(String message) {
        super(message);
    }
}
