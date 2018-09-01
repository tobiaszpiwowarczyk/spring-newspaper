package pl.toby.user;

import graphql.schema.idl.RuntimeWiring;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.toby.core.annotation.Controller;
import pl.toby.core.jwt.JwtUtil;
import pl.toby.core.misc.BaseController;
import pl.toby.core.misc.Response;
import pl.toby.user.datafetcher.AllUsersDataFetcher;
import pl.toby.user.datafetcher.UserByIdDataFetcher;
import pl.toby.user.datafetcher.UserByUsernameDataFetcher;
import pl.toby.user.datafetcher.module.UserDataFetcherModule;
import pl.toby.user.exception.UserAuthenticationException;
import pl.toby.user.exception.UserNotFoundException;
import pl.toby.user.role.UserRole;
import pl.toby.user.service.UserServiceImpl;

import java.security.Principal;
import java.util.*;

@Controller(path = "/api/users")
public class UserController extends BaseController {

    private final UserDataFetcherModule userDataFetcherModule;
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserDataFetcherModule userDataFetcherModule, UserServiceImpl userService) {
        this.userDataFetcherModule = userDataFetcherModule;
        this.userService = userService;
    }


    @Override
    public RuntimeWiring buildRuntimeWiring() {
        return userDataFetcherModule.buildRuntimeWiring();
    }


    // ----------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "all",
            method = RequestMethod.GET
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response findAll() {
        return new Response<>(
                HttpStatus.OK,
                executeQuery(AllUsersDataFetcher.QUERY)
                    .get(AllUsersDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "id/{id}",
            method = RequestMethod.GET
    )
    public Response findById(@PathVariable UUID id) {

        LinkedHashMap result = executeQuery(
                String.format(UserByIdDataFetcher.QUERY, id)
        );

        if (result.get(UserByIdDataFetcher.HEADER) == null)
            throw new UserNotFoundException();

        return new Response<>(
                HttpStatus.OK,
                result.get(UserByIdDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------

    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.GET
    )
    public Response findByUsername(Principal principal) {

        if(principal == null)
            throw new UserAuthenticationException();

        return new Response<>(
                HttpStatus.OK,
                executeQuery(
                        String.format(UserByUsernameDataFetcher.QUERY, principal.getName())
                ).get(UserByUsernameDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "login",
            method = RequestMethod.POST
    )
    public Response<Map<String, Object>> login(@RequestBody Map<String, String> credentials) {

        if(credentials == null || credentials.get("username") == null || credentials.get("password") == null) {
            throw new AccessDeniedException("Należy wypełnić wszystkie pola w formularzu");
        }
        else {
            User user = userService.findByUsername(credentials.get("username"));

            if(user != null && user.getPassword().equals(credentials.get("password"))) {
                if(!user.hasRole(UserRole.LOCKED)) {
                    String token = Jwts.builder()
                            .setSubject(credentials.get("username"))
                            .setExpiration(new Date(System.currentTimeMillis() + JwtUtil.EXPIRATION_TIME))
                            .claim(JwtUtil.ROLE_CLAIM, user.getRoles())
                            .signWith(SignatureAlgorithm.HS512, JwtUtil.SECRET_KEY)
                            .compact();

                    return new Response<>(
                            HttpStatus.OK,
                            new HashMap<String, Object>(){{
                                put("token", JwtUtil.TOKEN_PREFIX + token);
                                put("userData", new HashMap<String, Object>(){{
                                    put("username", user.getUsername());
                                    put("firstName", user.getFirstName());
                                    put("lastName", user.getLastName());
                                    put("avatar", user.getAvatar());
                                }});
                            }}
                    );
                }
                else {
                    throw new AccessDeniedException("Nie można zalogować się na to konto, gdyż jest ono zablokowane");
                }
            }
            else {
                throw new AccessDeniedException("Nazwa użytkownika lub hasło jest nieprawidłowe");
            }
        }

    }
}
