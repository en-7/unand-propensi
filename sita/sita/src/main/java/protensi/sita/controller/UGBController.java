package protensi.sita.controller;

import protensi.sita.model.EnumRole;
import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.PembimbingModel;
import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.UgbModel;
// import protensi.sita.service.UgbServiceImpl;
import protensi.sita.model.UserModel;
import protensi.sita.repository.PembimbingDb;
import protensi.sita.repository.UserDb;

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
import protensi.sita.service.BaseService;
import protensi.sita.service.MahasiswaServiceImpl;
import protensi.sita.service.ManageUserService;
import protensi.sita.service.ManageUserServiceImpl;

import java.util.*;

@Controller
public class UGBController {

    @Autowired
    private ManageUserServiceImpl manageUserService;

    @Autowired
    private UgbServiceImpl ugbService;

    @Autowired
    PembimbingDb pembimbingDb;

    @Autowired
    UserDb userDb;

    @Autowired
    public BaseService baseService;

    @GetMapping("/ugb/add")
    public String addUgbFormPage(Model model) {
        UgbModel ugbModel = new UgbModel();
        List<UserModel> listPembimbing = ugbService.getListPembimbing();
        // System.out.println("###LIST PEMBIMBING: "+ listPembimbing);
        model.addAttribute("ugb", ugbModel);
        model.addAttribute("listPembimbing", listPembimbing);
        model.addAttribute("roleUser", baseService.getCurrentRole());
        return "add-ugb-form";
    }

    @PostMapping("/ugb/add")
    public String addUgbSubmitPage(@ModelAttribute UgbModel ugb,
            @RequestParam("bukti_kp") MultipartFile bukti_kp,
            @RequestParam("transcript") MultipartFile transcript,
            @RequestParam("file_khs") MultipartFile file_khs,
            @RequestParam("file_ugb") MultipartFile file_ugb,
            Model model) {

        System.out.println("*** pembimbing_1 : " + ugb.getIdPembimbing1());
        System.out.println("*** pembimbing_2 : " + ugb.getIdPembimbing2());

        String result = ugbService.addUgb(ugb, bukti_kp, transcript, file_khs, file_ugb);
        return "add-ugb-success";
    }

    @GetMapping("/ugb/viewall")
    public String listUgb(Model model) {
        System.out.println("*** test ***");

        HashMap<String, List<UgbModel>> result = ugbService.viewAllUgb();
        Map.Entry<String, List<UgbModel>> entry = result.entrySet().iterator().next();
        // String roleUser = entry.getKey();
        List<UgbModel> listUgb = entry.getValue();

        System.out.println("*** list ugb retrieved: " + listUgb);

        model.addAttribute("listUgb", listUgb);
        model.addAttribute("roleUser", baseService.getCurrentRole());
        return "viewall-ugb";
    }

    // @GetMapping("/filter")
    // public String filterUgb(@RequestParam String status, Model model) {
    // List<SeminarProposalModel> filteredProposals =
    // seminarProposalService.findSemproByStatusDokumen(status);
    // model.addAttribute("listSempro", filteredProposals);
    // return "viewall-sempro";
    // }

}
