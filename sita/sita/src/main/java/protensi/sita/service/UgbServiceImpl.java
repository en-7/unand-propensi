package protensi.sita.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.UgbModel;
import protensi.sita.repository.UgbDb;

import java.util.List;

@Service
@Transactional
public class UgbServiceImpl {
    @Autowired
    UgbDb ugbDb;

    public void addUgb(UgbModel ugb){
        ugbDb.save(ugb);
    }

    public List<UgbModel> findAllUgb(){
        return ugbDb.findAll();
    }

    public UgbModel findByIdMahasiswa(MahasiswaModel mahasiswa){
        return ugbDb.findByMahasiswa(mahasiswa);
    }
    
}
