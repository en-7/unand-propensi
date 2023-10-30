package protensi.sita.repository;

import protensi.sita.model.SeminarProposalModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SeminarProposalDb extends JpaRepository<SeminarProposalModel, Long> {
    Optional<SeminarProposalModel> findByIdSeminarProposal(Long idSeminarProposal);
    List<SeminarProposalModel> findAllByStatusDokumen(String statusDokumen);
    
}
