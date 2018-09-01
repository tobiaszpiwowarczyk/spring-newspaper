package pl.toby.user.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.user.User;
import pl.toby.user.service.UserServiceImpl;

import java.util.UUID;

@Component
public class UserByIdDataFetcher implements DataFetcher<User> {

    public static final String HEADER = "userById";
    public static final String QUERY =
            "{" +
                HEADER +"(id: \"%s\") {" +
                    "id," +
                    "firstName," +
                    "lastName," +
                    "email," +
                    "avatar," +
                    "bgImage," +
                    "role, " +
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
    public UserByIdDataFetcher(UserServiceImpl userService) {
        this.userService = userService;
    }



    @Override
    public User get(DataFetchingEnvironment environment) {
        return userService.findById(UUID.fromString(environment.getArgument("id")));
    }
}
