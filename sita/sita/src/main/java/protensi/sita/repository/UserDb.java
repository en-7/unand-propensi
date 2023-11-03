package protensi.sita.repository;

import protensi.sita.model.EnumRole;
import protensi.sita.model.UserModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDb extends JpaRepository<UserModel, Long> {
    UserModel findByNama(String name);

    Optional<UserModel> findByIdUser(Integer idUser);

    List<UserModel> findAllByRole(EnumRole role);

    UserModel findByUsername(String username);
}
