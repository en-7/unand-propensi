package protensi.sita.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import protensi.sita.model.MahasiswaModel;
import java.util.Optional;

@Repository
public interface MahasiswaDb extends JpaRepository<MahasiswaModel, Long> {
    Optional<MahasiswaModel> findByIdUser(Integer idUser);
}

