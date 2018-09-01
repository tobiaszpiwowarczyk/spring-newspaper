package pl.toby.user.exception;

public class UserAuthenticationException extends RuntimeException {
    public UserAuthenticationException() {
        super("Aby wykonać dane żądanie, musisz być zalogowany");
    }
}
