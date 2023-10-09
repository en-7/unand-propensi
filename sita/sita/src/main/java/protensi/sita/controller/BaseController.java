package protensi.sita.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    // @GetMapping("/")
    // private String Home() {
    // return "home";
    // }

    @GetMapping("/error")
    private String Error() {
        return "error";
    }
}
