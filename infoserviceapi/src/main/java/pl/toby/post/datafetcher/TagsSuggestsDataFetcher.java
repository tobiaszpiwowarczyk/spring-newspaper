package pl.toby.post.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.post.service.PostServiceImpl;

import java.util.List;
import java.util.Map;

@Component
public class TagsSuggestsDataFetcher implements DataFetcher<List<String>> {

    public static final String HEADER = "tagsSuggests";
    public static final String QUERY = "{"+ HEADER +"(value: \"%s\")}";

    @Autowired
    private PostServiceImpl postService;

    @Override
    public List<String> get(DataFetchingEnvironment environment) {
        Map args = environment.getArguments();
        return postService.findTagsByTagName((String) args.get("value"));
    }
}
