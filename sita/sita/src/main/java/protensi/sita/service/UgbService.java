package protensi.sita.service;

import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.PembimbingModel;
import protensi.sita.model.UserModel;
import protensi.sita.model.UgbModel;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface UgbService {
    List<UserModel> getListPembimbing();

    String addUgb(UgbModel ugb, MultipartFile bukti_kp, MultipartFile transcript, MultipartFile file_khs, MultipartFile file_ugb);

    List<UgbModel> viewAllUgb();

    MahasiswaModel getMahasiswa(String username);

    UgbModel findByIdMahasiswa(MahasiswaModel mahasiswa);
    
    List<UgbModel> filterUgb(String status);

    void approveUgb(UgbModel ugb);

    void denyUgb(UgbModel ugb, String ctt);

    UgbModel getUgbById(Long idUgb);
}
