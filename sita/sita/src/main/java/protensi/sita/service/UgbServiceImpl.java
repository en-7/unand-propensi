package protensi.sita.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

import protensi.sita.model.EnumRole;
import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.PembimbingModel;
import protensi.sita.model.UgbModel;
import protensi.sita.model.UserModel;
import protensi.sita.repository.MahasiswaDb;
import protensi.sita.repository.UgbDb;
import protensi.sita.repository.UserDb;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UgbServiceImpl {
    @Autowired
    UgbDb ugbDb;

    @Autowired
    MahasiswaDb mahasiswaDb;

    @Autowired
    UserDb userDb;

    public List<UserModel> getListPembimbing(){
        List<UserModel> listUser = userDb.findAll();
        List<UserModel> listPembimbing = new ArrayList<>();

        for(UserModel u : listUser){
            Set<EnumRole> roles = u.getRoles();
            if (roles.contains(EnumRole.PEMBIMBING) == true){
                listPembimbing.add(u);
            }
        }
        return listPembimbing;
    }

    public String addUgb(UgbModel ugb, MultipartFile bukti_kp, MultipartFile transcript, MultipartFile file_khs, MultipartFile file_ugb){
        try{
            ugb.setBuktiKp(bukti_kp.getBytes());
            ugb.setTranskrip(transcript.getBytes());
            ugb.setFileKhs(file_khs.getBytes());
            ugb.setFileUgb(file_ugb.getBytes());
            ugb.setStatusDokumen("SUBMITTED");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            UserModel user = userDb.findByUsername(username);
            // System.out.println("### id mahassiswa noww: "+ user.getIdUser());

            MahasiswaModel mahasiswa = mahasiswaDb.findByIdUser(user.getIdUser());
            // System.out.println("### mahasiswa noww: "+mahasiswa);
            ugb.setMahasiswa(mahasiswa);

            Set<UserModel> set_pembimbing = new HashSet<>();
            set_pembimbing.add(userDb.findByIdUser(ugb.getIdPembimbing1()));
            set_pembimbing.add(userDb.findByIdUser(ugb.getIdPembimbing2()));
            ugb.setPembimbing(set_pembimbing);
            
            ugbDb.save(ugb);
            return "success BITCH";
        }catch (IOException e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
        
    }

    public HashMap<String, List<UgbModel>> viewAllUgb(){
        HashMap<String, List<UgbModel>> result_map = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserModel thisUser = userDb.findByUsername(username);
        Set<EnumRole> roles = thisUser.getRoles();
        List<UgbModel> retrievedUgb = new ArrayList<>();
        System.out.println("*** roles user == " + roles.toString());

        if (roles.contains(EnumRole.PEMBIMBING) == true){
            // *** the default filter would be 'BIMBINGAN'
            // *** returns the default list for dosen with role [PEMBIMBING] or [PEMBIMBING, PENGUJI]
            // *** list contains ugb mahasiswa bimbingan
            List<UgbModel> submitted_ugb = ugbDb.findAll();
            System.out.println("@@@@ initial list ugb pembimbing = "+submitted_ugb);
            for(UgbModel u : submitted_ugb){
                System.out.println("@@@@ UGB_now = "+u.getJudulUgb());
                if(u.getPembimbing().contains(thisUser) == true){
                    retrievedUgb.add(u);
                    System.out.println("@@@@ ugb dgn judul -"+u.getJudulUgb()+"- ditambah ke list");
                }
            }
            result_map.put(roles.toString(), retrievedUgb);
            return result_map;

        }else if(roles.contains(EnumRole.PENGUJI) == true){
            // *** the default filter would be 'EVALUATE'
            // *** returns the default list for dosen with role [PENGUJI]
            // *** list contains ugb with status 'EVALUATE'
            List<UgbModel> submitted_ugb = ugbDb.getUgbBasedOnStatus("EVALUATE");
            System.out.println("@@@@ initial list ugb penguji = "+submitted_ugb);
            for(UgbModel u : submitted_ugb){
                System.out.println("@@@@ UGB_now = "+u.getJudulUgb());
                if(u.getPembimbing().contains(thisUser) == true){
                    retrievedUgb.add(u);
                    System.out.println("@@@@ ugb dgn judul -"+u.getJudulUgb()+"- ditambah ke list");
                }
                
            }
            result_map.put(roles.toString(), retrievedUgb);
            return result_map;

        }else{
            // *** the default filter would be 'APPROVE'
            // returns the default list for dosen with role [KOORDINATOR]
            // *** list contains ugb with status 'EVALUATE'
            List<UgbModel> submitted_ugb = ugbDb.getUgbBasedOnStatus("APPROVE");
            System.out.println("@@@@ initial list ugb koordinator = "+submitted_ugb);
            result_map.put(roles.toString(), retrievedUgb);
            return result_map;
        }
    }

    public UgbModel findByIdMahasiswa(MahasiswaModel mahasiswa){
        return ugbDb.findByMahasiswa(mahasiswa);
    }

}
