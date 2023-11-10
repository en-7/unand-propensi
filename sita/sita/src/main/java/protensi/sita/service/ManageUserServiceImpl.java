package protensi.sita.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import protensi.sita.model.EnumRole;
import protensi.sita.model.UserModel;
import protensi.sita.repository.UserDb;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManageUserServiceImpl implements ManageUserService{
    @Autowired
    UserDb userDb;

    public void addUser(UserModel user) {
        userDb.save(user);
    }

    public List<UserModel> findAllUser() {
        return userDb.findAll();
    }

    public UserModel findUserById(Long idUser) {
        UserModel user = userDb.findByIdUser(idUser);
        if (user != null) {
            return user;
        } else
            return null;
    }

    public List<UserModel> findUserByRoles(EnumRole roles) {
        return userDb.findAllByRoles(roles);
    }

}
