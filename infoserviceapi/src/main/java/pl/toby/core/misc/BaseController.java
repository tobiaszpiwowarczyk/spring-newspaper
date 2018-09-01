package pl.toby.core.misc;


import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.toby.post.exception.PostNotFoundException;
import pl.toby.user.exception.UserAuthenticationException;
import pl.toby.user.exception.UserNotFoundException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

@ControllerAdvice
public abstract class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Value("classpath:schemas.graphql")
    private Resource schemaResource;

    private GraphQL graphQL;




    @PostConstruct
    private void loadSchema() throws IOException {
        InputStream schemaInputStream = schemaResource.getInputStream();
        File schemaFile = File.createTempFile("schemas", ".graphqls");
        FileUtils.copyInputStreamToFile(schemaInputStream, schemaFile);

        TypeDefinitionRegistry registry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    protected LinkedHashMap executeQuery(String query) {
        ExecutionResult result = graphQL.execute(query);
        LOGGER.warn(String.valueOf(result.getErrors()));
        return result.getData();
    }

    public abstract RuntimeWiring buildRuntimeWiring();






    @ExceptionHandler(PostNotFoundException.class)
    public Response<String> handlePostNotFoundException(Exception e) {
        return new Response<>(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
    }

    @ExceptionHandler(UserAuthenticationException.class)
    public Response<String> handleUserAuthenticationException(Exception e) {
        return new Response<>(HttpStatus.FORBIDDEN, e.getLocalizedMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public Response<String> handleUserNotFoundException(Exception e) {
        return new Response<>(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
    }

    @ExceptionHandler({AccessDeniedException.class, AuthenticationCredentialsNotFoundException.class})
    public Response<String> handleAccessDenied(Exception e) {
        return new Response<>(HttpStatus.FORBIDDEN, e.getLocalizedMessage());
    }
}
