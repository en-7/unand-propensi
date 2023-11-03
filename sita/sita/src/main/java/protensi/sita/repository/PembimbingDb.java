package protensi.sita.repository;

import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.PembimbingModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PembimbingDb extends JpaRepository<PembimbingModel, Long> {
   
}
