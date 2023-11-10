package protensi.sita.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import protensi.sita.model.EnumRole;
import protensi.sita.model.MahasiswaModel;
import protensi.sita.repository.MahasiswaDb;

import java.util.List;

@Service
@Transactional
public class MahasiswaServiceImpl implements MahasiswaService {
    @Autowired
    MahasiswaDb mahasiswaDb;

    public MahasiswaModel findMahasiswaById(Long idUser) {
        return mahasiswaDb.findByIdUser(idUser);
    }

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
