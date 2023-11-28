package protensi.sita.service;

import protensi.sita.model.AvailableBimbinganModel;
import protensi.sita.model.UgbModel;
import protensi.sita.model.UserModel;
import protensi.sita.repository.AvailableBimbinganDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

@Service
@Transactional
public class AvailableBimbinganServiceImpl implements AvailableBimbinganService {
    @Autowired
    AvailableBimbinganDb availableBimbinganDb;

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

    @Override
    public void add(AvailableBimbinganModel availableBimbingan) {
        availableBimbinganDb.save(availableBimbingan);
    }

    @Override
    public List<AvailableBimbinganModel> findAllByIdPembimbing(Long idUser){
        return availableBimbinganDb.findAllByPembimbing_IdUser(idUser);
    }

    @Override
    @Transactional
    public List<AvailableBimbinganModel> listAvailablePembimbing(UgbModel ugb){
        Set<UserModel> pembimbingSet = ugb.getPembimbing();
        List<UserModel> listPembimbing= new ArrayList<>(pembimbingSet);
        List<AvailableBimbinganModel> listAvailable = new ArrayList<AvailableBimbinganModel>();
        for (UserModel pembimbing : listPembimbing) {
            List<AvailableBimbinganModel> listAvailablePembimbing = availableBimbinganDb.findAllByPembimbing_IdUser(pembimbing.getIdUser());
            listAvailable.addAll(listAvailablePembimbing);
        }
        return listAvailable;
    }

    @Override
    public AvailableBimbinganModel findByStartBimbinganTime(LocalDateTime startBimbinganTime) {
        return availableBimbinganDb.findByStartBimbinganTime(startBimbinganTime);
    }

    @Override
    public AvailableBimbinganModel findByEndBimbinganTime(LocalDateTime endBimbinganTime) {
        return availableBimbinganDb.findByStartBimbinganTime(endBimbinganTime);
    }

    @Override
    public List<AvailableBimbinganModel> findByStartBimbinganTimeBetween(LocalDateTime startBimbinganTime, LocalDateTime endBimbinganTime) {
        return availableBimbinganDb.findByStartBimbinganTimeBetween(startBimbinganTime, endBimbinganTime);
    }

    @Override
    public List<AvailableBimbinganModel> findByEndBimbinganTimeBetween(LocalDateTime startBimbinganTime, LocalDateTime endBimbinganTime) {
        return availableBimbinganDb.findByEndBimbinganTimeBetween(startBimbinganTime, endBimbinganTime);
    }

}
