package pl.toby.post.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.post.service.PostServiceImpl;

import java.util.List;

@Component
public class TagsDataFetcher implements DataFetcher<List<String>> {

    public static final String HEADER = "tags";
    public static final String QUERY = "{"+ HEADER +"}";

    @Autowired
    private PostServiceImpl postService;

    @Override
    public List<String> get(DataFetchingEnvironment environment) {
        return postService.findAllTags();
    }
}
