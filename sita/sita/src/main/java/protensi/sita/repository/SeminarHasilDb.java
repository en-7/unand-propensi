package protensi.sita.repository;

import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.SeminarHasilModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeminarHasilDb extends JpaRepository<SeminarHasilModel, Long> {
    Optional<SeminarHasilModel> findByIdSeminarHasil(Long idSeminarHasil);

    Optional<SeminarHasilModel> findBySeminarProposal(SeminarProposalModel sempro);

    List<SeminarHasilModel> findAllByStatusDokumen(String statusDokumen);

    @Query("SELECT sh FROM SeminarHasilModel sh JOIN sh.ugb ugb JOIN ugb.pembimbing pembimbing WHERE pembimbing.id = :pembimbingId")
    List<SeminarHasilModel> findAllByPembimbing(@Param("pembimbingId") Long pembimbingId);

    @Query("SELECT sh FROM SeminarHasilModel sh JOIN sh.ugb ugb JOIN ugb.penguji penguji WHERE penguji.id = :pengujiId")
    List<SeminarHasilModel> findAllByPenguji(@Param("pengujiId") Long pengujiId);
}
