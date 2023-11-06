package protensi.sita.service;

import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.UgbModel;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface UgbService {

    String addUgb(UgbModel ugb, MultipartFile bukti_kp, MultipartFile transcript, MultipartFile file_khs, MultipartFile file_ugb);

    List<UgbModel> findAllUgb();

    MahasiswaModel getMahasiswa(String username);
}
