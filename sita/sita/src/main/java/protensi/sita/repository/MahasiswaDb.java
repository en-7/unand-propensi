package protensi.sita.repository;

import protensi.sita.model.MahasiswaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MahasiswaDb extends JpaRepository<MahasiswaModel, String> {
    MahasiswaModel findByUsername(String username);
}
