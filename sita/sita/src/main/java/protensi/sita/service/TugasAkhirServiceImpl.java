package protensi.sita.service;

import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.TugasAkhirModel;
import protensi.sita.repository.TugasAkhirDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Optional;

import javax.transaction.Transactional;

//import java.util.List;
//import java.util.Optional;

@Service
@Transactional
public class TugasAkhirServiceImpl implements TugasAkhirService {
    @Autowired
    TugasAkhirDb tugasAkhirDb;

    public void addSidangTA(TugasAkhirModel tugasAkhir) {
        tugasAkhirDb.save(tugasAkhir);
    }

    @Override
    @Override
    public List<TugasAkhirModel> findAllTugasAkhir() {
        return tugasAkhirDb.findAll();
    }

    @Override
    public TugasAkhirModel findTugasAkhirById(Long idTugasAkhir) {
        Optional<TugasAkhirModel> ta = tugasAkhirDb.findByIdTugasAkhir(idTugasAkhir);
        if (ta.isPresent()) {
            return ta.get();
        } else
            return null;
    }

    @Override
    public List<TugasAkhirModel> findTugasAkhirByStatusDokumen(String statusDokumen) {
        return tugasAkhirDb.findAllByStatusDokumen(statusDokumen);
    }
}