package protensi.sita.service;

import protensi.sita.model.EnumRole;
import protensi.sita.model.UserModel;
import java.util.List;


public interface ManageUserService {
    void addUser(UserModel user);

    List<UserModel> findAllUser();

    UserModel findUserById(Integer idUser);

    List<UserModel> findUserByRoles(EnumRole roles);
}
