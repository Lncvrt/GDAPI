package xyz.lncvrt.gdapi.exception;

public class RequestFailedException extends Exception {
    public RequestFailedException() {
        super("Failed to send request to the server");
    }
}
