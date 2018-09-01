package pl.toby.post.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super("Artykuł o podanym identyfikatorze został usunięty lub zablokowany przez administratora");
    }

}
