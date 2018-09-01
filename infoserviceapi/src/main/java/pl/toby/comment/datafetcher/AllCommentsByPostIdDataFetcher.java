package pl.toby.comment.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.comment.PostComment;
import pl.toby.comment.service.PostCommentServiceImpl;

import java.util.List;
import java.util.UUID;

@Component
public class AllCommentsByPostIdDataFetcher implements DataFetcher<List<PostComment>> {

    public static final String HEADER = "allCommentsByPostId";
    public static final String QUERY =
            "{" +HEADER +"(postId: \"%s\")" +
                "{" +
                    "id," +
                    "content," +
                    "date," +
                    "user {id, username, firstName, lastName, avatar}" +
                "}" +
            "}";

    private PostCommentServiceImpl postCommentService;

    @Autowired
    public AllCommentsByPostIdDataFetcher(PostCommentServiceImpl postCommentService) {
        this.postCommentService = postCommentService;
    }

    @Override
    public List<PostComment> get(DataFetchingEnvironment environment) {
        return postCommentService.findByPostId(
                UUID.fromString(environment.getArgument("postId"))
        );
    }
}
