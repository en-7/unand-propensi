package protensi.sita.repository;

import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.TugasAkhirModel;
import protensi.sita.model.UgbModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TugasAkhirDb extends JpaRepository<TugasAkhirModel, Long> {
    Optional<TugasAkhirModel> findByIdTugasAkhir(Long idTugasAkhir);

    Optional<TugasAkhirModel> findByUgb(UgbModel ugb);

    List<TugasAkhirModel> findAllByStatusDokumen(String statusDokumen);

    @Query("SELECT sp FROM TugasAkhirModel sp JOIN sp.ugb ugb JOIN ugb.pembimbing pembimbing WHERE pembimbing.id = :pembimbingId")
    List<TugasAkhirModel> findAllByPembimbing(@Param("pembimbingId") Long pembimbingId);

    // Mencari proposal seminar berdasarkan penguji
    @Query("SELECT sp FROM TugasAkhirModel sp JOIN sp.ugb ugb JOIN ugb.penguji penguji WHERE penguji.id = :pengujiId")
    List<TugasAkhirModel> findAllByPenguji(@Param("pengujiId") Long pengujiId);
}
