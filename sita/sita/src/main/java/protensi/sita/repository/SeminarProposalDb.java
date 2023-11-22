package protensi.sita.repository;

import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.UgbModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeminarProposalDb extends JpaRepository<SeminarProposalModel, Long> {
    Optional<SeminarProposalModel> findByIdSeminarProposal(Long idSeminarProposal);
    Optional<SeminarProposalModel> findByUgb(UgbModel ugb);
    List<SeminarProposalModel> findAllByStatusDokumen(String statusDokumen);
    
    @Query("SELECT sp FROM SeminarProposalModel sp JOIN sp.ugb ugb JOIN ugb.pembimbing pembimbing WHERE pembimbing.id = :pembimbingId")
    List<SeminarProposalModel> findAllByPembimbing(@Param("pembimbingId") Long pembimbingId);

    @Query("SELECT sp FROM SeminarProposalModel sp JOIN sp.ugb ugb JOIN ugb.penguji penguji WHERE penguji.id = :pengujiId")
    List<SeminarProposalModel> findAllByPenguji(@Param("pengujiId") Long pengujiId);
}
