package protensi.sita.service;

import protensi.sita.model.JadwalBimbinganModel;
import protensi.sita.repository.JadwalBimbinganDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
@Transactional
public class JadwalBimbinganServiceImpl implements JadwalBimbinganService {
    @Autowired
    JadwalBimbinganDb jadwalBimbinganDb;

    @Override
    public JadwalBimbinganModel findById(Long idJadwalBimbingan) {
        Optional<JadwalBimbinganModel> result = jadwalBimbinganDb.findById(idJadwalBimbingan);
        return result.orElse(null);
    }

    @Override
    public List<JadwalBimbinganModel> findAll() {
        return jadwalBimbinganDb.findAll();
    }

    @Override
    public void save(JadwalBimbinganModel jadwalBimbingan) {
        jadwalBimbinganDb.save(jadwalBimbingan);
    }

    @Override
    public void delete(Long idJadwalBimbingan) {
        jadwalBimbinganDb.deleteById(idJadwalBimbingan);
    }

    @Override
    public JadwalBimbinganModel findByAvaialableBimbingan(Long idAvailableBimbingan){
        Optional<JadwalBimbinganModel> result = jadwalBimbinganDb.findByAvailableBimbingan_IdAvailableBimbingan(idAvailableBimbingan);
        return result.orElse(null);
    }
}
