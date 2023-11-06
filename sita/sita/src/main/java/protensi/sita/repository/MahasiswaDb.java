package protensi.sita.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import protensi.sita.model.MahasiswaModel;
import java.util.Optional;

public interface MahasiswaDb extends JpaRepository<MahasiswaModel, Long> {
    Optional<MahasiswaModel> findByIdUser(Integer idUser);
    MahasiswaModel findByUsername(String username);
}
