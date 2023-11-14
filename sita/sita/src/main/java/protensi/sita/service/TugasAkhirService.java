package protensi.sita.service;

import java.util.List;

import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.TugasAkhirModel;
import protensi.sita.model.UgbModel;

public interface TugasAkhirService {
    void addSidangTA(TugasAkhirModel tugasAkhir);

    List<TugasAkhirModel> findAllTugasAkhir();

    TugasAkhirModel findTugasAkhirById(Long idTugasAkhir);

    List<TugasAkhirModel> findTugasAkhirByStatusDokumen(String statusDokumen);

    TugasAkhirModel findTAByUgb(UgbModel ugb);

    List<TugasAkhirModel> findAllByPembimbing(Long pembimbingId);

    List<TugasAkhirModel> findAllByPenguji(Long pengujiId);

    List<TugasAkhirModel> findTAByStatusDokumen(String statusDokumen);

    TugasAkhirModel updateTugasAkhir(TugasAkhirModel tugasAkhir);

    TugasAkhirModel saveNilaiAndStatus(Long idTugasAkhir, Long nilai, String statusTugasAkhir);

}
