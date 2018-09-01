package pl.toby.post.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.post.Post;
import pl.toby.post.service.PostServiceImpl;

import java.util.UUID;

@Component
public class PostDataFetcher implements DataFetcher<Post> {

    public static final String HEADER = "post";
    public static final String QUERY =
            "{" +
                HEADER + "(id: \"%s\") {" +
                    "id, " +
                    "title, " +
                    "description, " +
                    "content, " +
                    "tags, " +
                    "date, " +
                    "category, " +
                    "rating {" +
                        "thumbsUp {id, firstName, lastName, avatar}, " +
                        "thumbsDown {id, firstName, lastName, avatar}, " +
                        "value}, " +
                    "image {landscape}, " +
                    "user {id, firstName, lastName, avatar}, " +
                    "commentsCount, " +
                    "comments " +
                        "{id, " +
                        "content, " +
                        "date, " +
                        "user {id, username, firstName, lastName, avatar}" +
                    "}" +
                "}" +
            "}";

    @Autowired
    private PostServiceImpl postService;

    @Override
    public Post get(DataFetchingEnvironment environment) {
        return postService.findById(UUID.fromString(environment.getArgument("id")));
    }
}
