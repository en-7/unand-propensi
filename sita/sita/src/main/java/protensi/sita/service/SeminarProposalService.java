package protensi.sita.service;

import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.UgbModel;

import java.util.List;

public interface SeminarProposalService {
    void addSempro(SeminarProposalModel seminarProposal);
    SeminarProposalModel updateSempro(SeminarProposalModel seminarProposal);
    List<SeminarProposalModel> findAllSempro();
    List<SeminarProposalModel> findAllByPembimbing(Long pembimbingId);
    List<SeminarProposalModel> findAllByPenguji(Long pengujiId);
    SeminarProposalModel findSemproById(Long idSeminarProposal);
    SeminarProposalModel findSemproByUgb(UgbModel ugb);
    List<SeminarProposalModel> findSemproByStatusDokumen(String statusDokumen);
    SeminarProposalModel saveNilaiAndStatus(Long idSeminarProposal, Long nilai, String statusSeminarProposal);
}
