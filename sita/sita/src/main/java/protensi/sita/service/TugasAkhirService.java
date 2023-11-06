package protensi.sita.service;

import java.util.List;

import protensi.sita.model.TugasAkhirModel;

public interface TugasAkhirService {
    void addSidangTA(TugasAkhirModel tugasAkhir);

    List<TugasAkhirModel> findAllTugasAkhir();
}
