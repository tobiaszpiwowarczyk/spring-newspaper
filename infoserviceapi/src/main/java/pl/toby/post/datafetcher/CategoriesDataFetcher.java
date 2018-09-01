package pl.toby.post.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.post.category.PostCategory;
import pl.toby.post.service.PostServiceImpl;

@Component
public class CategoriesDataFetcher implements DataFetcher<PostCategory[]> {

    public static final String HEADER = "categories";
    public static final String QUERY = "{"+ HEADER +"}";

    @Autowired
    private PostServiceImpl postService;

    @Override
    public PostCategory[] get(DataFetchingEnvironment environment) {
        return postService.findAllPostCategories();
    }
}
