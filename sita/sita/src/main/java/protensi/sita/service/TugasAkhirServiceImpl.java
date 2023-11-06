package protensi.sita.service;

import protensi.sita.model.TugasAkhirModel;
import protensi.sita.repository.TugasAkhirDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<TugasAkhirModel> findAllTugasAkhir() {
        return tugasAkhirDb.findAll();
    }
}