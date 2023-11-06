package protensi.sita.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import protensi.sita.security.UserDetailsServiceImpl;

@Controller
public class BaseController {
<<<<<<< HEAD
//    @GetMapping("/")
//    private String log_in_out() {
//        return "log_in_out";
//    }

    @GetMapping("/home")
=======
    @Autowired
    public UserDetailsServiceImpl userDetailsService;

    @GetMapping("/")
>>>>>>> e66b4b69b422d9d35932bc651cbec452ee18383f
    private String home() {
        return "home";
    }

<<<<<<< HEAD
//    @GetMapping("/error")
//    private String Error() {
//        return "error";
//    }
=======
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping(value = "/create-dummy")
    public String addDummy() {
        userDetailsService.addDummy();
        return "login";
    }
>>>>>>> e66b4b69b422d9d35932bc651cbec452ee18383f
}
