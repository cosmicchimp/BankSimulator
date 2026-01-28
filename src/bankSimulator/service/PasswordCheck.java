package bankSimulator.service;

public class PasswordCheck {
    private String passwordInput;
    private String associatedUser;
    public PasswordCheck(String passwordInput, String user) {
        this.passwordInput = passwordInput;
        this.associatedUser = user;
    }
    public boolean VerifyPassword(String password) {
        return true;
    }
}
