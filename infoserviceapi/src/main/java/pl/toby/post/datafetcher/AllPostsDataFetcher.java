package pl.toby.post.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import pl.toby.core.misc.ResPage;
import pl.toby.post.service.PostServiceImpl;


@Component
public class AllPostsDataFetcher implements DataFetcher<ResPage> {

    public static final String HEADER = "allPosts";
    public static final String QUERY =
            "{" +
                HEADER +"(page: %s, size: %s) {" +
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
                postService.findAll(
                        new PageRequest(
                                environment.getArgument("page"),
                                environment.getArgument("size")
                        )
                )
        );
    }
}
