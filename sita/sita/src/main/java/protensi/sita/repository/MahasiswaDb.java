package protensi.sita.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import protensi.sita.model.MahasiswaModel;


public interface MahasiswaDb extends JpaRepository<MahasiswaModel, Long> {
    MahasiswaModel findByIdUser(Long idUser);

    MahasiswaModel findByUsername(String username);
}
