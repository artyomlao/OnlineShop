package onlineshop.exception;

public class UserDoesNotExist extends Exception {
    public UserDoesNotExist(String message) {
        super(message);
    }
}
