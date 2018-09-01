package pl.toby.comment.datafetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.comment.PostComment;
import pl.toby.comment.service.PostCommentServiceImpl;

@Component
public class RemoveCommentDataFetcher implements DataFetcher<String> {

    public static final String HEADER = "removeComment";
    public static final String QUERY = "mutation "+ HEADER +" {"+ HEADER +"(comment: %s)}";


    private PostCommentServiceImpl postCommentService;
    private ObjectMapper objectMapper;

    @Autowired
    public RemoveCommentDataFetcher(PostCommentServiceImpl postCommentService) {
        this.postCommentService = postCommentService;
        this.objectMapper = new ObjectMapper();
    }


    @Override
    public String get(DataFetchingEnvironment environment) {
        PostComment comment = objectMapper.convertValue(environment.getArgument("comment"), PostComment.class);
        return postCommentService.removeComment(comment);
    }
}
