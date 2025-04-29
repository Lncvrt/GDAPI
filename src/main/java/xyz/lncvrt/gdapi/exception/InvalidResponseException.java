package xyz.lncvrt.gdapi.exception;

public class InvalidResponseException extends Exception {
    public InvalidResponseException() {
        super("The server returned unsupported or invalid data");
    }
}
