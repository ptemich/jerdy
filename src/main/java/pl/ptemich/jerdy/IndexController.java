package pl.ptemich.jerdy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/button-click")
    public String loadOnClick() {
        return "action";
    }

}
