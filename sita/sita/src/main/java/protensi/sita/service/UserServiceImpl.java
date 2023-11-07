package protensi.sita.service;

import protensi.sita.security.xml.Attributes;
import protensi.sita.security.xml.ServiceResponse;
import protensi.sita.setting.Setting;
import protensi.sita.service.UserService;
import protensi.sita.model.AdminModel;
import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.EnumRole;
import protensi.sita.model.UserModel;
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
public class UserServiceImpl {
    @Autowired
    MahasiswaDb mahasiswaDb;
    @Autowired
    AdminDb adminDb;
    @Autowired
    UserDb userDb;

    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    public UserModel findByUsername(String username) {
        return userDb.findByUsername(username);
    }

    public void addDummy() {
        AdminModel admin = new AdminModel();
        admin.setNama("legend");
        admin.setUsername("legend");
        admin.setPassword("legend");
        encrypt(admin.getPassword());
        admin.setRole(EnumRole.ADMIN);
        admin.setEmail("legend@email.com");
        adminDb.save(admin);

        MahasiswaModel mahasiswa = new MahasiswaModel();
        mahasiswa.setNama("hoho");
        mahasiswa.setUsername("hoho");
        mahasiswa.setPassword("hoho");
        encrypt(mahasiswa.getPassword());
        mahasiswa.setRole(EnumRole.MAHASISWA);
        mahasiswa.setEmail("hoho@email.com");
        mahasiswa.setNim(001);
        mahasiswa.setTahap("UGB");
        mahasiswaDb.save(mahasiswa);
    }
}
