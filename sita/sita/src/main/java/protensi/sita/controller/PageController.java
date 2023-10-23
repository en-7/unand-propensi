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

    @GetMapping("/error")
    private String Error() {
        return "error";
    }

    // validate username and password login
    @GetMapping("/validate-ticket")
    public ModelAndView adminLoginSSO(
            @RequestParam(value = "ticket", required = false) String ticket,
            HttpServletRequest request) {

        // // Memasukkan data dummy dari Idol dan sublabel
        // dummyService.createDummySublabelAndIdol();

        var serviceResponse = this.webClient.get().uri(
                String.format(
                        Setting.SERVER_VALIDATE_TICKET,
                        ticket,
                        Setting.CLIENT_LOGIN))
                .retrieve().bodyToMono(ServiceResponse.class).block();

        assert serviceResponse != null;
        var attributes = serviceResponse.getAuthenticationSuccess().getAttributes();
        String username = serviceResponse.getAuthenticationSuccess().getUser();

        // UserModel user = userService.getUserByUsername(username);

        var admin = new AdminModel();
        admin.setIdUser(1);
        admin.setNama(attributes.getNama());
        admin.setRole("Admin");
        admin.setEmail(username + "@ui.ac.id");
        admin.setPassword("SITA");

        // if (user == null) {
        // var admin = new AdminModel();
        // admin.setEmail(username + "@ui.ac.id");
        // admin.setNama(attributes.getNama());
        // admin.setPassword("AdverStar");
        // admin.setUsername(username);
        // admin.setRole("Admin");
        // userService.addAdmin(admin);

        // }

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, "SITA");

        var securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        var httpSession = request.getSession(true);
        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
        return new ModelAndView("redirect:/");
    }

    @GetMapping(value = "/login-sso")
    public ModelAndView loginSSO() {
        return new ModelAndView("redirect:" + Setting.SERVER_LOGIN + Setting.CLIENT_LOGIN);
    }

    @GetMapping(value = "/logout-sso")
    public ModelAndView logoutSSO(Principal principal) {
        // UserModel user = userService.getUserByUsername(principal.getName());
        // if (!(user.getRole().equals("Admin"))) {
        // return new ModelAndView("redirect:/logout");
        // }
        return new ModelAndView("redirect:" + Setting.SERVER_LOGOUT + Setting.CLIENT_LOGOUT);
    }
}
