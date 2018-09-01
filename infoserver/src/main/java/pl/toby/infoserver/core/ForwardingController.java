package pl.toby.infoserver.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ForwardingController {

    @RequestMapping("/{path:[^\\.]+}/**")
    public String forwardToIndex() {
        return "forward:/";
    }
}
