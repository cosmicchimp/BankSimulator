package bankSimulator.exceptions;

public class AccountException extends Exception {
    public AccountException() {
        //Blank default exception thrower
    };

    public AccountException(String message) {
        super(message);
    }
}
