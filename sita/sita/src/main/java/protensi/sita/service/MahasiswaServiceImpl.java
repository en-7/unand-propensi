package protensi.sita.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import protensi.sita.model.MahasiswaModel;
import protensi.sita.repository.MahasiswaDb;

import javax.transaction.Transactional;

@Service
@Transactional
public class MahasiswaServiceImpl {
    @Autowired
    MahasiswaDb mahasiswaDb;

    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public MahasiswaModel addMahasiswa(MahasiswaModel mahasiswa) {
        mahasiswa.setPassword(encoder().encode(mahasiswa.getPassword()));
        mahasiswaDb.save(mahasiswa);
        return mahasiswa;
    }

    public List<MahasiswaModel> findAllMahasiswa() {
        return mahasiswaDb.findAll();
    }

    public MahasiswaModel findMahasiswaByUsername(String username) {
        return mahasiswaDb.findByUsername(username);
    }
}
