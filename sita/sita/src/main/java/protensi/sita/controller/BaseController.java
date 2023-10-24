package protensi.sita.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/")
    private String log_in_out() {
        return "log_in_out";
    }

    @GetMapping("/home")
    private String home() {
        return "home";
    }

//    @GetMapping("/error")
//    private String Error() {
//        return "error";
//    }
}