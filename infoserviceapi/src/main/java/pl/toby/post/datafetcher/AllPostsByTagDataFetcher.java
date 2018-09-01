package pl.toby.post.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pl.toby.core.misc.ResPage;
import pl.toby.post.service.PostServiceImpl;

@Component
public class AllPostsByTagDataFetcher implements DataFetcher<ResPage> {

    public static final String HEADER = "allPostsByTag";
    public static final String QUERY =
            "{"+HEADER +"(page: %s, size: %s, value: \"%s\") {" +
                    "content " +
                        "{" +
                            "id, " +
                            "title, " +
                            "description, " +
                            "tags, " +
                            "date, " +
                            "category, " +
                            "rating {value}," +
                            "image {thumbnail}, " +
                            "user {id, firstName, lastName, avatar}, " +
                            "commentsCount" +
                        "}, " +
                    "totalPages, " +
                    "totalElements, " +
                    "currentPage, " +
                    "first, " +
                    "last" +
                "}" +
            "}";

    @Autowired
    private PostServiceImpl postService;

    @Override
    public ResPage get(DataFetchingEnvironment environment) {
        return new ResPage(
                postService.findPostsByTagName(
                        new PageRequest(
                                environment.getArgument("page"),
                                environment.getArgument("size")
                        ),
                        environment.getArgument("value")
                )
        );
    }
}
