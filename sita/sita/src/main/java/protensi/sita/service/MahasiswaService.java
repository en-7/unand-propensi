package protensi.sita.service;

import protensi.sita.model.EnumRole;
import protensi.sita.model.MahasiswaModel;
import java.util.List;


public interface MahasiswaService {
    MahasiswaModel findMahasiswaById(Long idUser);
    MahasiswaModel addMahasiswa(MahasiswaModel mahasiswa);
    List<MahasiswaModel> findAllMahasiswa();
    MahasiswaModel findMahasiswaByUsername(String username);

}