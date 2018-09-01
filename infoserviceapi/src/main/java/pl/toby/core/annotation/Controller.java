package pl.toby.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
@RestController
@RequestMapping(
        path = {},
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = {
                MediaType.APPLICATION_JSON_UTF8_VALUE,
                MediaType.IMAGE_PNG_VALUE
        }
)
public @interface Controller {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}
