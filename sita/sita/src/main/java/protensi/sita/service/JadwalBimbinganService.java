package protensi.sita.service;

import protensi.sita.model.JadwalBimbinganModel;
import java.util.List;

public interface JadwalBimbinganService {
    JadwalBimbinganModel findById(Long idJadwalBimbingan);
    JadwalBimbinganModel findByAvaialableBimbingan(Long idAvailableBimbingan);
    List<JadwalBimbinganModel> findAll();
    void save(JadwalBimbinganModel jadwalBimbingan);
    void delete(Long idJadwalBimbingan);
}
