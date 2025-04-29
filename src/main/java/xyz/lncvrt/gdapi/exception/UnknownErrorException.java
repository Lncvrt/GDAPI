package xyz.lncvrt.gdapi.exception;

public class UnknownErrorException extends Exception {
    public UnknownErrorException() {
        super("An unknown error occurred on the server side");
    }
}
