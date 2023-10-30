package protensi.sita.controller;

import protensi.sita.model.AdminModel;
import protensi.sita.model.UserModel;

import protensi.sita.security.xml.ServiceResponse;
// import apap.tugaskelompok.adverstar.service.DummyService;
// import apap.tugaskelompok.adverstar.service.UserService;
import protensi.sita.setting.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class PageController {
    private WebClient webClient = WebClient.builder().build();

    @GetMapping("/")
    private String home() {
        return "home";
    }

    @GetMapping("/login")
    private String login() {
        return "login";
    }

    // @GetMapping("/error")
    // private String Error() {
    // return "error";
    // }
}
