package protensi.sita.service;

import protensi.sita.model.AvailableBimbinganModel;
import protensi.sita.repository.AvailableBimbinganDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvailableBimbinganServiceImpl implements AvailableBimbinganService {

    private final AvailableBimbinganDb availableBimbinganDb;

    @Autowired
    public AvailableBimbinganServiceImpl(AvailableBimbinganDb availableBimbinganDb) {
        this.availableBimbinganDb = availableBimbinganDb;
    }

    @Override
    public AvailableBimbinganModel findById(Long idAvailableBimbingan) {
        Optional<AvailableBimbinganModel> result = availableBimbinganDb.findById(idAvailableBimbingan);
        return result.orElse(null);
    }

    @Override
    public List<AvailableBimbinganModel> findAll() {
        return availableBimbinganDb.findAll();
    }

    @Override
    public AvailableBimbinganModel save(AvailableBimbinganModel availableBimbingan) {
        return availableBimbinganDb.save(availableBimbingan);
    }

    @Override
    public void delete(Long idAvailableBimbingan) {
        availableBimbinganDb.deleteById(idAvailableBimbingan);
    }
}
