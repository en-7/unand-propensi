package protensi.sita.repository;

import protensi.sita.model.TugasAkhirModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TugasAkhirDb extends JpaRepository<TugasAkhirModel, Long> {
    Optional<TugasAkhirModel> findByIdTugasAkhir(Long idTugasAkhir);

    List<TugasAkhirModel> findAllByStatusDokumen(String statusDokumen);

}
