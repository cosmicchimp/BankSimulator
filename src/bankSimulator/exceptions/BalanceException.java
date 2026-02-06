package bankSimulator.exceptions;

public class BalanceException extends Exception {
    public BalanceException() {
        //Blank default exception thrower
    };

    public BalanceException(String message) {
        super(message);
    }
}
