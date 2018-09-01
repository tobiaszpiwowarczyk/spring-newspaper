package pl.toby.post;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.toby.core.annotation.Controller;
import pl.toby.core.misc.BaseController;
import pl.toby.core.misc.Response;
import pl.toby.post.datafetcher.*;
import pl.toby.post.datafetcher.module.PostDataFetcherModule;
import pl.toby.post.exception.PostNotFoundException;
import pl.toby.user.exception.UserAuthenticationException;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.UUID;

@Controller(path = "/api/posts")
public class PostController extends BaseController {


    private PostDataFetcherModule dataFetcherModule;

    @Autowired
    public PostController(PostDataFetcherModule dataFetcherModule) {
        this.dataFetcherModule = dataFetcherModule;
    }


    @Override
    public RuntimeWiring buildRuntimeWiring() {
        return dataFetcherModule.buildRuntimeWiring();
    }



    // ----------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "page/{page}",
            method = RequestMethod.GET
    )
    public Response findAll(
            @PathVariable int page,
            @RequestParam(required = false, defaultValue = "20") int size
    ) {
        return new Response<>(
                HttpStatus.OK,
                executeQuery(
                        String.format(AllPostsDataFetcher.QUERY, page, size)
                ).get(AllPostsDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "id/{id}",
            method = RequestMethod.GET
    )
    public Response findById(@PathVariable UUID id) {

        LinkedHashMap result = executeQuery(String.format(PostDataFetcher.QUERY, id));

        if (result.get(PostDataFetcher.HEADER) == null) {
            throw new PostNotFoundException();
        }

        return new Response<>(
                HttpStatus.OK,
                result.get(PostDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "tags",
            method = RequestMethod.GET
    )
    public Response getAllTags() {
        return new Response<>(
                HttpStatus.OK,
                executeQuery(TagsDataFetcher.QUERY).get(TagsDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "tags-suggests/{tagName}",
            method = RequestMethod.GET
    )
    public Response findTagsByTagName(@PathVariable String tagName) {
        return new Response<>(
                HttpStatus.OK,
                executeQuery(String.format(TagsSuggestsDataFetcher.QUERY, tagName))
                        .get(TagsSuggestsDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "page/{page}/tag/{tagName}",
            method = RequestMethod.GET
    )
    public Response findPostsByTagName(
            @PathVariable int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @PathVariable String tagName) {
        return new Response<>(
                HttpStatus.OK,
                executeQuery(
                        String.format(AllPostsByTagDataFetcher.QUERY, page, size, tagName)
                ).get(AllPostsByTagDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "page/{page}/category/{category}",
            method = RequestMethod.GET
    )
    public Response findPostsByCategory(
            @PathVariable int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @PathVariable String category) {

        return new Response<>(
                HttpStatus.OK,
                executeQuery(
                        String.format(AllPostsByCategoryDataFetcher.QUERY, page, size, category.toUpperCase())
                ).get(AllPostsByCategoryDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "categories",
            method = RequestMethod.GET
    )
    public Response getAllPostCategories() {
        return new Response<>(
                HttpStatus.OK,
                executeQuery(CategoriesDataFetcher.QUERY)
                    .get(CategoriesDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "id/{id}/like",
            method = RequestMethod.POST
    )
    public Response like(
            @PathVariable UUID id,
            Principal principal,
            @RequestParam String thumbType) {

        if(principal == null)
            throw new UserAuthenticationException();

        return new Response<>(
                HttpStatus.OK,
                executeQuery(String.format(LikePostDataFetcher.QUERY, id, principal.getName(), thumbType))
                    .get(LikePostDataFetcher.HEADER)
        );
    }
}
