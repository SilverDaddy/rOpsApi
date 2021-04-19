package infra.rOpsApi;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController implements ErrorController {
    /*@RequestMapping(value={"", "/", "/error", "/*"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }*/
    @GetMapping(value={"", "/", "/*"})
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String redirect() {
        return "index";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
