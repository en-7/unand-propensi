package protensi.sita.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import protensi.sita.model.JadwalSidangModel;
import protensi.sita.repository.jadwalSeminarSidangDb;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class jadwalSidangSeminarServiceImpl implements jadwalSidangSeminarService{

    @Autowired
    jadwalSeminarSidangDb jadwalSeminarSidangDb;

    @Override
    public List<JadwalSidangModel> getListJadwalSidang() {
        return jadwalSeminarSidangDb.findAll();
    }

    @Override
    public JadwalSidangModel getJadwalSidangById(long id) {
        Optional<JadwalSidangModel> jadwalSidangid = jadwalSeminarSidangDb.findById(id);
        if(jadwalSidangid.isPresent()){
            return jadwalSidangid.get();
        }
        else{
            return null;
        }
    }

    @Override
    public void setJadwalSidang(JadwalSidangModel sempro) {
        jadwalSeminarSidangDb.save(sempro);
    }

    @Override
    public void deletesJadwalSidangSeminar(Long id) {
        jadwalSeminarSidangDb.deleteById(id);
    }
}
