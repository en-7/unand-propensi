package protensi.sita.service;

import protensi.sita.model.AvailableBimbinganModel;
import protensi.sita.model.UgbModel;

import java.time.LocalDateTime;
import java.util.List;

public interface AvailableBimbinganService {
    AvailableBimbinganModel findById(Long idAvailableBimbingan);
    List<AvailableBimbinganModel> findAll();
    AvailableBimbinganModel save(AvailableBimbinganModel availableBimbingan);
    void delete(Long idAvailableBimbingan);
    void add(AvailableBimbinganModel availableBimbingan);
    List<AvailableBimbinganModel> findAllByIdPembimbing(Long idUser);
    List<AvailableBimbinganModel> listAvailablePembimbing(UgbModel ugb);
    AvailableBimbinganModel findByStartBimbinganTime(LocalDateTime startBimbinganTime);
    AvailableBimbinganModel findByEndBimbinganTime(LocalDateTime endBimbinganTime);
    List<AvailableBimbinganModel> findByStartBimbinganTimeBetween(LocalDateTime startBimbinganTime, LocalDateTime endBimbinganTime);
    List<AvailableBimbinganModel> findByEndBimbinganTimeBetween(LocalDateTime startBimbinganTime, LocalDateTime endBimbinganTime);
}
