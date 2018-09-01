package pl.toby.user.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.user.User;
import pl.toby.user.service.UserServiceImpl;

import java.util.List;

@Component
public class AllUsersDataFetcher implements DataFetcher<List<User>> {

    public static final String HEADER = "allUsers";
    public static final String QUERY =
            "{" +
                HEADER +" {" +
                    "id," +
                    "firstName," +
                    "lastName," +
                    "avatar" +
                "}" +
            "}";


    private UserServiceImpl userService;

    @Autowired
    public AllUsersDataFetcher(UserServiceImpl userService) {
        this.userService = userService;
    }


    @Override
    public List<User> get(DataFetchingEnvironment environment) {
        return userService.findAll();
    }
}
