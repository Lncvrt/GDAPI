package xyz.lncvrt.gdapi.exception;

public class PasswordTooShortException extends Exception {
    public PasswordTooShortException() {
        super("The password provided is less than 6 characters long");
    }
}
