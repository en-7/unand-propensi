package protensi.sita.repository;

import protensi.sita.model.UgbModel;
import protensi.sita.model.MahasiswaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UgbDb extends JpaRepository<UgbModel, Long> {
    <Optional> UgbModel findByMahasiswa(MahasiswaModel mahasiswa);
   
}
