package protensi.sita.service;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class BaseService {

    public String getCurrentRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String roleUser = authentication.getAuthorities().toString();
        System.out.println("*** role saat ini: "+ roleUser);
        return roleUser;
    }
    
}
