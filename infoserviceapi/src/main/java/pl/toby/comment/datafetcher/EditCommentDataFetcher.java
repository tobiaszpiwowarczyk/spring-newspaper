package pl.toby.comment.datafetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.comment.PostComment;
import pl.toby.comment.service.PostCommentServiceImpl;

@Component
public class EditCommentDataFetcher implements DataFetcher<PostComment> {

    public static final String HEADER = "editComment";
    public static final String QUERY =
            "mutation "+ HEADER +"{" +
                 HEADER + "(comment: %s) {" +
                     "id," +
                     "content," +
                     "date," +
                     "user {id, username, firstName, lastName, avatar}" +
                 "}" +
            "}";


    private ObjectMapper objectMapper;

    private PostCommentServiceImpl postCommentService;

    @Autowired
    public EditCommentDataFetcher(PostCommentServiceImpl postCommentService) {
        this.postCommentService = postCommentService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public PostComment get(DataFetchingEnvironment environment) {
        PostComment comment = objectMapper.convertValue(environment.getArgument("comment"), PostComment.class);
        return postCommentService.editComment(comment);
    }
}
