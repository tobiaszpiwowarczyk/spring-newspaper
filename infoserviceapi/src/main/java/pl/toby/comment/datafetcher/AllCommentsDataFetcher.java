package pl.toby.comment.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.comment.PostComment;
import pl.toby.comment.service.PostCommentServiceImpl;

import java.util.List;

@Component
public class AllCommentsDataFetcher implements DataFetcher<List<PostComment>> {

    public static final String HEADER = "allComments";
    public static final String QUERY =
            "{" +HEADER +
                "{" +
                    "id, " +
                    "content, " +
                    "date, " +
                    "user {id, firstName, lastName, avatar}, " +
                    "post {id, title, image {thumbnail}}" +
                "}" +
            "}";

    private PostCommentServiceImpl postCommentService;

    @Autowired
    public AllCommentsDataFetcher(PostCommentServiceImpl postCommentService) {
        this.postCommentService = postCommentService;
    }

    @Override
    public List<PostComment> get(DataFetchingEnvironment environment) {
        return postCommentService.findAll();
    }
}
