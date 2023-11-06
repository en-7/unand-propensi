package protensi.sita.service;

import protensi.sita.model.EnumRole;
import protensi.sita.model.MahasiswaModel;
import java.util.List;


public interface MahasiswaService {
    MahasiswaModel findMahasiswaById(Integer idUser);
}