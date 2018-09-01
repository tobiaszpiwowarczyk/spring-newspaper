package pl.toby.core.jwt;


public class JwtUtil {

    public static final long EXPIRATION_TIME = 864_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String SECRET_KEY = "ThisIsASecretKey";
    public static final String ROLE_CLAIM = "roles";

}
