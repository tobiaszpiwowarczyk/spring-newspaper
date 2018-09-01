package pl.toby.user.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.user.User;
import pl.toby.user.service.UserServiceImpl;

@Component
public class UserByUsernameDataFetcher implements DataFetcher<User> {

    public static final String HEADER = "userByUsername";
    public static final String QUERY =
            "{" +
                HEADER +"(username: \"%s\") {" +
                    "id," +
                    "username," +
                    "password," +
                    "firstName," +
                    "lastName," +
                    "email," +
                    "avatar," +
                    "bgImage," +
                    "role," +
                    "userSex, " +
                    "posts {" +
                        "id, " +
                        "title, " +
                        "description, " +
                        "tags, " +
                        "date, " +
                        "category, " +
                        "rating {value}, " +
                        "image {thumbnail}, " +
                        "commentsCount" +
                    "}" +
                "}" +
            "}";

    private UserServiceImpl userService;

    @Autowired
    public UserByUsernameDataFetcher(UserServiceImpl userService) {
        this.userService = userService;
    }


    @Override
    public User get(DataFetchingEnvironment environment) {
        return userService.findByUsername(environment.getArgument("username"));
    }
}
