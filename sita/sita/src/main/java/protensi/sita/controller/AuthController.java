package protensi.sita.controller;

import protensi.sita.setting.Setting;
import protensi.sita.service.UserService;
import protensi.sita.service.WhitelistService;
import protensi.sita.model.UserModel;
import protensi.sita.model.WhitelistModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class AuthController {
    @Autowired
    ServerProperties serverProperties;
    private WebClient webClient = WebClient.builder().build();
    @Autowired
    UserService userService;
    @Autowired
    WhitelistService whitelistService;

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/validate-ticket")
    public ModelAndView adminLoginSSO(
            @RequestParam(value = "ticket", required = false) String ticket,
            HttpServletRequest request) {

        userService.adminLoginSSO(ticket, request, this.webClient);
        return new ModelAndView("redirect:/");
    }

    @GetMapping(value = "/login-sso")
    public ModelAndView loginSSO() {
        return new ModelAndView("redirect:" + Setting.SERVER_LOGIN + Setting.CLIENT_LOGIN);
    }

    @GetMapping(value = "/logout-sso")
    public ModelAndView logoutSSO(Principal principal) {
        UserModel user = userService.findByUsername(principal.getName());
        if (!user.getRole().equals("ADMIN")) {
            return new ModelAndView("redirect:/logout");
        }
        return new ModelAndView("redirect:" + Setting.SERVER_LOGOUT + Setting.CLIENT_LOGOUT);
    }

    @GetMapping(value = "/create-dummy")
    public String addDummy() {
        userService.addDummy();
        return "login";
    }

    @GetMapping(value = "/create-dummy-whitelist")
    public String addDummyWhitelist() {
        userService.addWhitelist();
        return "login";
    }

    @GetMapping("/whitelist/add")
    public String addWhitelistFormPage(Model model) {
        WhitelistModel whitelist = new WhitelistModel();
        model.addAttribute("whitelist", whitelist);
        return "add-whitelist";
    }

    @PostMapping(value = "/whitelist/add", params = { "save" })
    public String addWhitelistSubmitPage(@ModelAttribute WhitelistModel whitelist, Model model) {
        whitelistService.addwhitelist(whitelist.getUsername());
        model.addAttribute("username", whitelist.getUsername());
        return "add-whitelist-success";
    }
}
