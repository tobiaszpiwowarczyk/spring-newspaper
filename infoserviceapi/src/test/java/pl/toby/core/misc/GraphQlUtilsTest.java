package pl.toby.core.misc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import pl.toby.comment.PostComment;
import pl.toby.user.User;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(GraphQlUtils.class)
public class GraphQlUtilsTest {

    @Test
    public void renderTest() throws Exception {
        PostComment comment = new PostComment();
        comment.setContent("Mój komentarz");
        comment.setUser(new User());
        comment.getUser().setUsername("anowak");

        String expected = "{content:\"Mój komentarz\",user:{username:\"anowak\"}}";

        assertThat(GraphQlUtils.render(comment)).isEqualTo(expected);
    }

}