package protensi.sita.service;

import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.PembimbingModel;
import protensi.sita.model.UserModel;
import protensi.sita.model.UgbModel;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public interface UgbService {
    List<UserModel> getListPembimbing();

    String addUgb(UgbModel ugb, MultipartFile bukti_kp, MultipartFile transcript, MultipartFile file_khs, MultipartFile file_ugb);

    List<UgbModel> viewAllUgb();

    UserModel getCurrentUser();

    UgbModel getAddFormObjects();

    void updateUgbKoordinator(Long idUgb, Long idP1, Long idP2);
    
    void updateUgbMahasiswa(Long idUgb, String judul, MultipartFile bukti_kp, MultipartFile transcript, MultipartFile file_khs, MultipartFile file_ugb);

    void downloadUgbFiles(String type, Long id, HttpServletResponse response);
    // MahasiswaModel getMahasiswa(String username);

    UgbModel findByIdMahasiswa(MahasiswaModel mahasiswa);
    
    List<UgbModel> filterUgb(String status);

    void approveUgb(UgbModel ugb);

    void denyUgb(UgbModel ugb, String ctt);

    UgbModel getUgbById(Long idUgb);
}
