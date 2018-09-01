package pl.toby.post.datafetcher.module;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.core.misc.BaseController;
import pl.toby.post.datafetcher.*;

@Component
public class PostDataFetcherModule extends BaseController {

    private AllPostsDataFetcher allPostsDataFetcher;
    private PostDataFetcher postDataFetcher;
    private TagsDataFetcher tagsDataFetcher;
    private TagsSuggestsDataFetcher tagsSuggestsDataFetcher;
    private AllPostsByTagDataFetcher allPostsByTagDataFetcher;
    private AllPostsByCategoryDataFetcher allPostsByCategoryDataFetcher;
    private CategoriesDataFetcher categoriesDataFetcher;
    private LikePostDataFetcher likePostDataFetcher;

    @Autowired
    public PostDataFetcherModule(AllPostsDataFetcher allPostsDataFetcher, PostDataFetcher postDataFetcher, TagsDataFetcher tagsDataFetcher, TagsSuggestsDataFetcher tagsSuggestsDataFetcher, AllPostsByTagDataFetcher allPostsByTagDataFetcher, AllPostsByCategoryDataFetcher allPostsByCategoryDataFetcher, CategoriesDataFetcher categoriesDataFetcher, LikePostDataFetcher likePostDataFetcher) {
        this.allPostsDataFetcher = allPostsDataFetcher;
        this.postDataFetcher = postDataFetcher;
        this.tagsDataFetcher = tagsDataFetcher;
        this.tagsSuggestsDataFetcher = tagsSuggestsDataFetcher;
        this.allPostsByTagDataFetcher = allPostsByTagDataFetcher;
        this.allPostsByCategoryDataFetcher = allPostsByCategoryDataFetcher;
        this.categoriesDataFetcher = categoriesDataFetcher;
        this.likePostDataFetcher = likePostDataFetcher;
    }


    @Override
    public RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher(AllPostsDataFetcher.HEADER, allPostsDataFetcher)
                        .dataFetcher(PostDataFetcher.HEADER, postDataFetcher)
                        .dataFetcher(TagsDataFetcher.HEADER, tagsDataFetcher)
                        .dataFetcher(TagsSuggestsDataFetcher.HEADER, tagsSuggestsDataFetcher)
                        .dataFetcher(AllPostsByTagDataFetcher.HEADER, allPostsByTagDataFetcher)
                        .dataFetcher(AllPostsByCategoryDataFetcher.HEADER, allPostsByCategoryDataFetcher)
                        .dataFetcher(CategoriesDataFetcher.HEADER, categoriesDataFetcher)
                )
                .type("Mutation", typeWiring -> typeWiring
                        .dataFetcher(LikePostDataFetcher.HEADER, likePostDataFetcher)
                )
                .build();
    }

}
