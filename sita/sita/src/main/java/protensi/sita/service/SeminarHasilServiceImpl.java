package protensi.sita.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import protensi.sita.model.SeminarHasilModel;
import protensi.sita.model.SeminarProposalModel;
import protensi.sita.repository.SeminarHasilDb;

import java.util.List;
import java.util.Optional;

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


    public SeminarHasilModel findSemhasById(Long idSeminarHasil){
        Optional<SeminarHasilModel> semhas = seminarHasilDb.findById(idSeminarHasil);
        if(semhas.isPresent()){
            return semhas.get();
        }
        else return null;
    }

}
