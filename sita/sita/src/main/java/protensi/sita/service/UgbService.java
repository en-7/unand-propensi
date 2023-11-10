package protensi.sita.service;

import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.UgbModel;

import java.util.List;

public interface UgbService {

    void addUgb(UgbModel ugb);

    List<UgbModel> findAllUgb();

    UgbModel findByIdMahasiswa(MahasiswaModel mahasiswa);
}
