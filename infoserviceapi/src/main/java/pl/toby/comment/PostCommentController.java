package pl.toby.comment;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.toby.comment.datafetcher.*;
import pl.toby.comment.datafetcher.module.PostCommentDataFetcherModule;
import pl.toby.core.annotation.Controller;
import pl.toby.core.misc.BaseController;
import pl.toby.core.misc.GraphQlUtils;
import pl.toby.core.misc.Response;
import pl.toby.user.exception.UserAuthenticationException;

import java.security.Principal;
import java.util.UUID;

@Controller(path = "api/comments")
public class PostCommentController extends BaseController {

    private PostCommentDataFetcherModule dataFetcherModule;

    @Autowired
    public PostCommentController(PostCommentDataFetcherModule dataFetcherModule) {
        this.dataFetcherModule = dataFetcherModule;
    }

    @Override
    public RuntimeWiring buildRuntimeWiring() {
        return dataFetcherModule.buildRuntimeWiring();
    }



    // ----------------------------------------------------------------------------------------------------------------

    @RequestMapping(
            value = {"", "/"},
            method = RequestMethod.GET
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response findAll() {
        return new Response<>(
                HttpStatus.OK,
                executeQuery(AllCommentsDataFetcher.QUERY)
                        .get(AllCommentsDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "{postId}",
            method = RequestMethod.GET
    )
    public Response findByPostId(@PathVariable UUID postId) {
        return new Response<>(
                HttpStatus.OK,
                executeQuery(
                        String.format(AllCommentsByPostIdDataFetcher.QUERY, postId)
                ).get(AllCommentsByPostIdDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "{postId}/add-comment",
            method = RequestMethod.POST
    )
    public Response addComment(
            @PathVariable UUID postId,
            @AuthenticationPrincipal Principal principal,
            @RequestBody PostComment postComment) throws IllegalAccessException {

        if (principal == null)
            throw new UserAuthenticationException();

        return new Response<>(
                HttpStatus.CREATED,
                executeQuery(
                        String.format(AddCommentDataFetcher.QUERY, postId, principal.getName(), GraphQlUtils.render(postComment))
                ).get(AddCommentDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "edit-comment",
            method = RequestMethod.POST
    )
    @PreAuthorize("#principal.getName() == authentication.name && #principal.getName().equals(#postComment.getUser().getUsername())")
    public Response editComment(
            @AuthenticationPrincipal Principal principal,
            @RequestBody PostComment postComment
    ) throws IllegalAccessException {

        if (principal == null)
            throw new UserAuthenticationException();

        return new Response<>(
                HttpStatus.OK,
                executeQuery(
                        String.format(EditCommentDataFetcher.QUERY, GraphQlUtils.render(postComment))
                ).get(EditCommentDataFetcher.HEADER)
        );
    }

    // ----------------------------------------------------------------------------------------------------------------

    @RequestMapping(
            value = "remove-comment",
            method = RequestMethod.DELETE
    )
    @PreAuthorize("#principal.getName() == authentication.name && #principal.getName().equals(#postComment.getUser().getUsername())")
    public Response removeComment(
            @AuthenticationPrincipal Principal principal,
            @RequestBody PostComment postComment
    ) throws IllegalAccessException {

        if(principal == null)
            throw new UserAuthenticationException();

        return new Response<>(
                HttpStatus.OK,
                executeQuery(
                        String.format(RemoveCommentDataFetcher.QUERY, GraphQlUtils.render(postComment))
                ).get(RemoveCommentDataFetcher.HEADER)
        );
    }
}
