package xyz.lncvrt.gdapi.exception;

public class UsernameTooShortException extends Exception {
    public UsernameTooShortException() {
        super("The username provided is less than 3 characters long");
    }
}
