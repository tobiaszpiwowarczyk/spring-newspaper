package pl.toby.comment.datafetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.comment.PostComment;
import pl.toby.comment.service.PostCommentServiceImpl;
import pl.toby.user.User;
import pl.toby.user.service.UserServiceImpl;

import java.util.UUID;

@Component
public class AddCommentDataFetcher implements DataFetcher<PostComment> {

    public static final String HEADER = "addComment";
    public static final String QUERY =
            "mutation " + HEADER + "{" +
                HEADER + "(postId: \"%s\", username: \"%s\", content: %s) {" +
                    "id, " +
                    "content, " +
                    "date, " +
                    "user {id, username, firstName, lastName, avatar}" +
                "}" +
            "}";

    private ObjectMapper objectMapper = new ObjectMapper();

    private PostCommentServiceImpl postCommentService;
    private UserServiceImpl userService;

    @Autowired
    public AddCommentDataFetcher(PostCommentServiceImpl postCommentService, UserServiceImpl userService) {
        this.postCommentService = postCommentService;
        this.userService = userService;
    }

    @Override
    public PostComment get(DataFetchingEnvironment environment) {
        User user = userService.findByUsername(environment.getArgument("username"));
        PostComment comment = objectMapper.convertValue(environment.getArgument("content"), PostComment.class);

        return postCommentService.addComment(
                UUID.fromString(environment.getArgument("postId")),
                user,
                comment
        );
    }
}
