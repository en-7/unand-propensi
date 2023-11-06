package protensi.sita.service;

import protensi.sita.model.SeminarProposalModel;
import protensi.sita.repository.SeminarProposalDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SeminarProposalServiceImpl implements SeminarProposalService {
    @Autowired
    SeminarProposalDb seminarProposalDb;

    public void addSempro(SeminarProposalModel seminarProposal){
        seminarProposalDb.save(seminarProposal);
    }
    @Override
    public SeminarProposalModel updateSempro(SeminarProposalModel seminarProposal) {
        seminarProposalDb.save(seminarProposal);
        return seminarProposal;
    }
    @Override
    public List<SeminarProposalModel> findAllSempro(){
        return seminarProposalDb.findAll();
    }
    @Override
    public SeminarProposalModel findSemproById(Long idSeminarProposal){
        Optional<SeminarProposalModel> sempro = seminarProposalDb.findByIdSeminarProposal(idSeminarProposal);
        if (sempro.isPresent()) {
            return sempro.get();
        } else return null;
    }
    @Override
    public List<SeminarProposalModel> findSemproByStatusDokumen(String statusDokumen) {
        return seminarProposalDb.findAllByStatusDokumen(statusDokumen);
    }
    @Override
    public SeminarProposalModel saveNilaiAndStatus(Long idSeminarProposal, Long nilai, String statusSeminarProposal) {
        SeminarProposalModel seminarProposal = seminarProposalDb.findByIdSeminarProposal(idSeminarProposal).orElse(null);
        if (seminarProposal != null) {
            seminarProposal.setNilai(nilai);
            seminarProposal.setStatusSeminarProposal(statusSeminarProposal);
            return seminarProposalDb.save(seminarProposal);
        }
        return null;
    }
    @Override
    public MultipartFile getFileByIdAndType(Long idSeminarProposal, String fileType) {
        SeminarProposalModel seminarProposal = seminarProposalDb.findById(idSeminarProposal).orElse(null);

        if (seminarProposal != null) {
            byte[] fileBytes = null;

            switch (fileType) {
                case "draftProposalTa":
                    fileBytes = seminarProposal.getDraftProposalTa();
                    break;
                case "buktiKrs":
                    fileBytes = seminarProposal.getBuktiKrs();
                    break;
                case "persetujuanPembimbing":
                    fileBytes = seminarProposal.getPersetujuanPembimbing();
                    break;
            }

            if (fileBytes != null) {
                return new ByteArrayMultipartFile(fileBytes);
            }
        }
        return null;
    }

}
