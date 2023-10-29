package protensi.sita.service;

import protensi.sita.model.SeminarProposalModel;

import java.util.List;

public interface SeminarProposalService {
    void addSempro(SeminarProposalModel seminarProposal);
    SeminarProposalModel updateSempro(SeminarProposalModel seminarProposal);
    List<SeminarProposalModel> findAllSempro();
    SeminarProposalModel findSemproById(Long idSeminarProposal);


}
