package protensi.sita.repository;

import protensi.sita.model.EnumRole;
import protensi.sita.model.UserModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDb extends JpaRepository<UserModel, Long> {
    UserModel findByNama(String name);

    UserModel findByIdUser(Long idUser);

    List<UserModel> findAllByRoles(EnumRole roles);

    UserModel findByUsername(String username);

}
