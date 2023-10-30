package protensi.sita.service;

import protensi.sita.model.UserModel;

public interface UserService {
    UserModel addUser(UserModel user);

    public String encrypt(String password);
}
