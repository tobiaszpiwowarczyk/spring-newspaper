package pl.toby.post.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.post.rating.PostRating;
import pl.toby.post.service.PostServiceImpl;
import pl.toby.user.User;
import pl.toby.user.service.UserServiceImpl;

import java.util.UUID;

@Component
public class LikePostDataFetcher implements DataFetcher<PostRating> {

    public static final String HEADER = "like";
    public static final String QUERY =
            "mutation "+ HEADER +" {"+
                HEADER+"(postId: \"%s\", username: \"%s\", thumbType: \"%s\") {" +
                    "thumbsUp {id, firstName, lastName, avatar}," +
                    "thumbsDown {id, firstName, lastName, avatar}, " +
                    "value" +
                "}" +
            "}";


    private PostServiceImpl postService;
    private UserServiceImpl userService;




    @Autowired
    public LikePostDataFetcher(PostServiceImpl postService, UserServiceImpl userService) {
        this.postService = postService;
        this.userService = userService;
    }




    @Override
    public PostRating get(DataFetchingEnvironment environment) {
        User user = userService.findByUsername(environment.getArgument("username"));

        return postService.like(
                UUID.fromString(environment.getArgument("postId")),
                user,
                environment.getArgument("thumbType")
        );
    }
}
