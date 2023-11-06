package protensi.sita.controller;

import protensi.sita.model.EnumRole;
import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.UgbModel;
// import protensi.sita.service.UgbServiceImpl;
import protensi.sita.model.UserModel;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import protensi.sita.service.SeminarProposalServiceImpl;
import protensi.sita.service.UgbServiceImpl;
import protensi.sita.service.MahasiswaServiceImpl;
import protensi.sita.service.ManageUserService;
import protensi.sita.service.ManageUserServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class UGBController {

    // @Autowired
    // private UgbServiceImpl ugbService;

    @Autowired
    private MahasiswaServiceImpl mahasiswaService;

    @Autowired
    private ManageUserServiceImpl manageUserService;

    @Autowired
    private UgbServiceImpl ugbService;

    @GetMapping("/ugb/add")
    public String addUgbFormPage(Model model){
        UgbModel ugbModel = new UgbModel();

        EnumRole enumRole = EnumRole.PEMBIMBING;
        List<UserModel> listPembimbing = manageUserService.findUserByRole(enumRole);

        model.addAttribute("ugb", ugbModel);
        model.addAttribute("listPembimbing", listPembimbing);

        return "add-ugb-form";
    }

    // @PostMapping("/ugb/add")
    // public String addUgbSubmitPage(@ModelAttribute UgbModel ugb, 
    //                             @RequestParam("bukti_kp") MultipartFile bukti_kp,
    //                             @RequestParam("transcript") MultipartFile transcript,
    //                             @RequestParam("file_khs") MultipartFile file_khs,
    //                             @RequestParam("file_ugb") MultipartFile file_ugb,
    //                             Authentication authentication,
    //                             Model model) throws IOException{
    //     ugb.setBuktiKp(bukti_kp.getBytes());
    //     ugb.setTranskrip(transcript.getBytes());
    //     ugb.setFileKhs(file_khs.getBytes());
    //     ugb.setFileUgb(file_ugb.getBytes());
    //     ugb.setStatusDokumen("SUBMITTED");

        
    //     String uname = authentication.getUsername();
    //     MahasiswaModel user = mahasiswaDb.getUserByUsername(uname);
    //     ugb.setMahasiswa(user);
    //     ugbService.addUgb(ugb);
        
    //     return "add-ugb-success";
    // }

    @PostMapping("/ugb/add")
    public String addUgbSubmitPage(@ModelAttribute UgbModel ugb, 
                                @RequestParam("bukti_kp") MultipartFile bukti_kp,
                                @RequestParam("transcript") MultipartFile transcript,
                                @RequestParam("file_khs") MultipartFile file_khs,
                                @RequestParam("file_ugb") MultipartFile file_ugb,
                                Authentication authentication,
                                Model model) {

        String result = ugbService.addUgb(ugb, bukti_kp, transcript, file_khs, file_ugb);
   
        return "add-ugb-success";
    }

    @GetMapping("/ugb/viewall")
    public String listUgb(Model model) {
        List<UgbModel> listUgb = ugbService.findAllUgb();
        model.addAttribute("listUgb", listUgb);
        return "viewall-ugb";
    }

}
