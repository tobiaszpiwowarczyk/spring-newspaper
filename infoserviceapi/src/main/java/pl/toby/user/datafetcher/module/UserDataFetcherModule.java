package pl.toby.user.datafetcher.module;

import graphql.schema.idl.RuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.toby.core.misc.BaseController;
import pl.toby.user.datafetcher.AllUsersDataFetcher;
import pl.toby.user.datafetcher.UserByIdDataFetcher;
import pl.toby.user.datafetcher.UserByUsernameDataFetcher;

@Component
public class UserDataFetcherModule extends BaseController {


    private AllUsersDataFetcher allUsersDataFetcher;
    private UserByIdDataFetcher userByIdDataFetcher;
    private UserByUsernameDataFetcher userByUsernameDataFetcher;

    @Autowired
    public UserDataFetcherModule(AllUsersDataFetcher allUsersDataFetcher,
                                 UserByIdDataFetcher userByIdDataFetcher,
                                 UserByUsernameDataFetcher userByUsernameDataFetcher) {
        this.allUsersDataFetcher = allUsersDataFetcher;
        this.userByIdDataFetcher = userByIdDataFetcher;
        this.userByUsernameDataFetcher = userByUsernameDataFetcher;
    }

    @Override
    public RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher(AllUsersDataFetcher.HEADER, allUsersDataFetcher)
                        .dataFetcher(UserByIdDataFetcher.HEADER, userByIdDataFetcher)
                        .dataFetcher(UserByUsernameDataFetcher.HEADER, userByUsernameDataFetcher)
                )
                .build();
    }
}
