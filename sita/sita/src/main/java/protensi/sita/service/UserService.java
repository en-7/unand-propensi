package protensi.sita.service;

import protensi.sita.security.xml.Attributes;
import protensi.sita.security.xml.ServiceResponse;
import protensi.sita.setting.Setting;
import protensi.sita.service.UserService;
import protensi.sita.model.AdminModel;
import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.EnumRole;
import protensi.sita.model.UserModel;
import protensi.sita.model.WhitelistModel;
import protensi.sita.repository.AdminDb;
import protensi.sita.repository.MahasiswaDb;
import protensi.sita.repository.UserDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class UserService {
    @Autowired
    MahasiswaDb mahasiswaDb;
    @Autowired
    AdminDb adminDb;
    @Autowired
    UserDb userDb;
    @Autowired
    WhitelistService whitelistService;

    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    public void adminLoginSSO(String ticket, HttpServletRequest request, WebClient webClient) {
        ServiceResponse serviceResponse = webClient.get().uri(
                String.format(
                        Setting.SERVER_VALIDATE_TICKET,
                        ticket,
                        Setting.CLIENT_LOGIN))
                .retrieve().bodyToMono(ServiceResponse.class).block();
        Attributes attributes = serviceResponse.getAuthenticationSuccess().getAttributes();
        String username = serviceResponse.getAuthenticationSuccess().getUser();
        UserModel user = userDb.findByUsername(username);
        WhitelistModel whitelist = whitelistService.findByUsername(username);

        if (user == null && whitelist != null) {
            AdminModel admin = new AdminModel();
            admin.setUsername(username);
            admin.setNama(username);
            admin.setRole(EnumRole.ADMIN);
            admin.setEmail(username + "@ui.ac.id");
            admin.setPassword(encrypt("sita"));
            adminDb.save(admin);
        } else {
            if (!user.getRole().equals(EnumRole.ADMIN) || whitelist == null) {
                throw new Error("Unauthorized");
            }
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, "sita");

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
    }

    public UserModel findByUsername(String username) {
        return userDb.findByUsername(username);
    }

    public void addDummy() {
        AdminModel admin = new AdminModel();
        admin.setIdUser(1);
        admin.setNama("admin");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole(EnumRole.ADMIN);
        admin.setEmail("admin");
        admin.setCreated_at("1/1/24");
        adminDb.save(admin);

        MahasiswaModel mahasiswa = new MahasiswaModel();
        mahasiswa.setIdUser(2);
        mahasiswa.setNama("mahasiswa");
        mahasiswa.setUsername("mahasiswa");
        mahasiswa.setPassword("mahasiswa");
        mahasiswa.setRole(EnumRole.MAHASISWA);
        mahasiswa.setEmail("mahasiswa");
        mahasiswa.setCreated_at("1/1/24");
        mahasiswa.setNim(001);
        mahasiswa.setTahap("UGB");
        mahasiswaDb.save(mahasiswa);
    }

    public void addWhitelist() {
        whitelistService.addwhitelist("assyifa.r");
        whitelistService.addwhitelist("daffa.aldin");
        whitelistService.addwhitelist("josias.m");
        whitelistService.addwhitelist("lilis.safitri");
        whitelistService.addwhitelist("nur.wasi");
    }

}
