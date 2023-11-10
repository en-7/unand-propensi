package protensi.sita.service;

import protensi.sita.model.AvailableBimbinganModel;
import java.util.List;

public interface AvailableBimbinganService {
    AvailableBimbinganModel findById(Long idAvailableBimbingan);
    List<AvailableBimbinganModel> findAll();
    AvailableBimbinganModel save(AvailableBimbinganModel availableBimbingan);
    void delete(Long idAvailableBimbingan);
    void add(AvailableBimbinganModel availableBimbingan);
    List<AvailableBimbinganModel> findAllByIdPembimbing(Long idUser);
}
