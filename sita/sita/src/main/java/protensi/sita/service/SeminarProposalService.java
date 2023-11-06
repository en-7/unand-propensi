package protensi.sita.service;

import protensi.sita.model.SeminarProposalModel;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface SeminarProposalService {
    void addSempro(SeminarProposalModel seminarProposal);
    SeminarProposalModel updateSempro(SeminarProposalModel seminarProposal);
    List<SeminarProposalModel> findAllSempro();
    SeminarProposalModel findSemproById(Long idSeminarProposal);
    List<SeminarProposalModel> findSemproByStatusDokumen(String statusDokumen);
    SeminarProposalModel saveNilaiAndStatus(Long idSeminarProposal, Long nilai, String statusSeminarProposal);
    MultipartFile getFileByIdAndType(Long idSeminarProposal, String fileType);
}
