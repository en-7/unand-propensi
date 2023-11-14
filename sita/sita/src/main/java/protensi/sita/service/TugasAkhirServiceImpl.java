package protensi.sita.service;

import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.TugasAkhirModel;
import protensi.sita.model.UgbModel;
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

    @Override
    public TugasAkhirModel findTAByUgb(UgbModel ugb) {
        Optional<TugasAkhirModel> tugasAkhir = tugasAkhirDb.findByUgb(ugb);
        if (tugasAkhir.isPresent()) {
            return tugasAkhir.get();
        } else
            return null;
    }

    @Override
    public List<TugasAkhirModel> findAllByPembimbing(Long pembimbingId) {
        return tugasAkhirDb.findAllByPembimbing(pembimbingId);
    }

    @Override
    public List<TugasAkhirModel> findAllByPenguji(Long pengujiId) {
        return tugasAkhirDb.findAllByPenguji(pengujiId);
    }

    @Override
    public List<TugasAkhirModel> findTAByStatusDokumen(String statusDokumen) {
        return tugasAkhirDb.findAllByStatusDokumen(statusDokumen);
    }

    @Override
    public TugasAkhirModel updateTugasAkhir(TugasAkhirModel tugasAkhir) {
        tugasAkhirDb.save(tugasAkhir);
        return tugasAkhir;
    }

    @Override
    public TugasAkhirModel saveNilaiAndStatus(Long idTugasAkhir, Long nilai, String statusTugasAkhir) {
        TugasAkhirModel tugasAkhir = tugasAkhirDb.findByIdTugasAkhir(idTugasAkhir).orElse(null);
        if (tugasAkhir != null) {
            tugasAkhir.setNilai(nilai);
            tugasAkhir.setStatusTugasAkhir(statusTugasAkhir);
            return tugasAkhirDb.save(tugasAkhir);
        }
        return null;
    }

}