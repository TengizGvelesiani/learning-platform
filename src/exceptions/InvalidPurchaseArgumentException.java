package exceptions;

public class InvalidPurchaseArgumentException extends RuntimeException {

    public InvalidPurchaseArgumentException(String message) {
        super(message);
    }
}
