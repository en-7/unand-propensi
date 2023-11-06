package protensi.sita.security;

import protensi.sita.model.EnumRole;
import protensi.sita.model.UserModel;
import protensi.sita.repository.AdminDb;
import protensi.sita.repository.UserDb;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDb userDb;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserDb userDatabase) {
        super();
        this.userDb = userDatabase;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userDb.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        System.out.println("###########" + user.getRoles());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRoles()));
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

/* 
    public void addDummy(){
        UserModel koordinator = new UserModel("koordinator", EnumRole.KOORDINATOR, "koordinator", passwordEncoder.encode("koordinator"), "koordinator@gmail.com");
        userDb.save(koordinator);
        UserModel admin = new UserModel("admin", EnumRole.ADMIN, "admin", passwordEncoder.encode("admin"), "admin@gmail.com");
        userDb.save(admin);
        UserModel pembimbing = new UserModel("pembimbing", EnumRole.PEMBIMBING, "pembimbing", passwordEncoder.encode("pembimbing"), "pembimbing@gmail.com");
        userDb.save(pembimbing);
        UserModel penguji = new UserModel("penguji", EnumRole.PENGUJI, "penguji", passwordEncoder.encode("penguji"), "penguji@gmail.com");
        userDb.save(penguji);
        UserModel mahasiswa = new UserModel("mahasiswa", EnumRole.MAHASISWA, "mahasiswa", passwordEncoder.encode("mahasiswa"), "mahasiswa@gmail.com");
        userDb.save(mahasiswa);
    }
*/

}
