package pl.toby.comment.datafetcher.module;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.comment.datafetcher.*;
import pl.toby.core.misc.BaseController;

@Component
public class PostCommentDataFetcherModule extends BaseController {

    private AllCommentsDataFetcher allCommentsDataFetcher;
    private AllCommentsByPostIdDataFetcher allCommentsByPostIdDataFetcher;
    private AddCommentDataFetcher addCommentDataFetcher;
    private EditCommentDataFetcher editCommentDataFetcher;
    private RemoveCommentDataFetcher removeCommentDataFetcher;

    @Autowired
    public PostCommentDataFetcherModule(AllCommentsDataFetcher allCommentsDataFetcher,
                                        AllCommentsByPostIdDataFetcher allCommentsByPostIdDataFetcher,
                                        AddCommentDataFetcher addCommentDataFetcher,
                                        EditCommentDataFetcher editCommentDataFetcher,
                                        RemoveCommentDataFetcher removeCommentDataFetcher) {
        this.allCommentsDataFetcher = allCommentsDataFetcher;
        this.allCommentsByPostIdDataFetcher = allCommentsByPostIdDataFetcher;
        this.addCommentDataFetcher = addCommentDataFetcher;
        this.editCommentDataFetcher = editCommentDataFetcher;
        this.removeCommentDataFetcher = removeCommentDataFetcher;
    }


    @Override
    public RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher(AllCommentsDataFetcher.HEADER, allCommentsDataFetcher)
                        .dataFetcher(AllCommentsByPostIdDataFetcher.HEADER, allCommentsByPostIdDataFetcher)
                )
                .type("Mutation", typeWiring -> typeWiring
                        .dataFetcher(AddCommentDataFetcher.HEADER, addCommentDataFetcher)
                        .dataFetcher(EditCommentDataFetcher.HEADER, editCommentDataFetcher)
                        .dataFetcher(RemoveCommentDataFetcher.HEADER, removeCommentDataFetcher)
                )
                .build();
    }
}
