package protensi.sita.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import protensi.sita.model.SeminarHasilModel;
import protensi.sita.model.SeminarProposalModel;
import protensi.sita.repository.SeminarHasilDb;

import java.util.List;

@Service
@Transactional
public class SeminarHasilServiceImpl {
    @Autowired
    SeminarHasilDb seminarHasilDb;

    public void addSeminarHasil(SeminarHasilModel seminarHasil) {
        seminarHasilDb.save(seminarHasil);
    }

    public List<SeminarHasilModel> findAllSeminarHasil() {
        return seminarHasilDb.findAll();
    }

    public List<SeminarHasilModel> findAllByPembimbing(Long pembimbingId) {
        return seminarHasilDb.findAllByPembimbing(pembimbingId);
    }

    public List<SeminarHasilModel> findAllByPenguji(Long pengujiId) {
        return seminarHasilDb.findAllByPenguji(pengujiId);
    }
}
