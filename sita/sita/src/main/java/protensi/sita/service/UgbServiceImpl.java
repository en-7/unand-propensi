package protensi.sita.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.UgbModel;
import protensi.sita.model.UserModel;
import protensi.sita.repository.MahasiswaDb;
import protensi.sita.repository.UgbDb;
import protensi.sita.repository.UserDb;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class UgbServiceImpl {
    @Autowired
    UgbDb ugbDb;

    @Autowired
    MahasiswaDb mahasiswaDb;

    @Autowired
    UserDb userDb;

    public String addUgb(UgbModel ugb, MultipartFile bukti_kp, MultipartFile transcript, MultipartFile file_khs, MultipartFile file_ugb){
        try{
            ugb.setBuktiKp(bukti_kp.getBytes());
            ugb.setTranskrip(transcript.getBytes());
            ugb.setFileKhs(file_khs.getBytes());
            ugb.setFileUgb(file_ugb.getBytes());
            ugb.setStatusDokumen("SUBMITTED");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            MahasiswaModel mahasiswa = mahasiswaDb.findByUsername(username);
            ugb.setMahasiswa(mahasiswa);
            return "success";
        }catch (IOException e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
        
    }

    public List<UgbModel> findAllUgb(){
        return ugbDb.findAll();
    }
    
}
