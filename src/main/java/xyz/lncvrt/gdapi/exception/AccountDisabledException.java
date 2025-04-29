package xyz.lncvrt.gdapi.exception;

public class AccountDisabledException extends Exception {
    public AccountDisabledException() {
        super("The provided login credentials lead to an account that is disabled");
    }
}
