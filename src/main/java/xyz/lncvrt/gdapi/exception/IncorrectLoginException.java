package xyz.lncvrt.gdapi.exception;

public class IncorrectLoginException extends Exception {
    public IncorrectLoginException() {
        super("The provided login credentials are incorrect");
    }
}
