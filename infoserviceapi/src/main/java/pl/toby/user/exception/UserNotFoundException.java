package pl.toby.user.exception;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Użytkownik o podanym identyfikatorze nie istnieje lub został zablokowany przez administratora");
    }

}
