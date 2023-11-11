package protensi.sita.controller;

import protensi.sita.model.UgbModel;
import protensi.sita.model.UserModel;
import protensi.sita.repository.PembimbingDb;
import protensi.sita.repository.UserDb;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import protensi.sita.service.UgbServiceImpl;
import protensi.sita.service.BaseService;

import java.util.*;


@Controller
public class UGBController {

    @Autowired
    private UgbServiceImpl ugbService;

    @Autowired
    PembimbingDb pembimbingDb;

    @Autowired
    UserDb userDb;

    @Autowired
    public BaseService baseService;

    @GetMapping("/ugb/add")
    public String addUgbFormPage(Model model){
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

        System.out.println("*** pembimbing_1 : "+ ugb.getIdPembimbing1());
        System.out.println("*** pembimbing_2 : "+ ugb.getIdPembimbing2());


        String result = ugbService.addUgb(ugb, bukti_kp, transcript, file_khs, file_ugb);
        return "add-ugb-success";
    }

    @GetMapping("/ugb/update/{idUgb}")
    public String updateUgbFormPage(@PathVariable Long idUgb, Model model){
        UgbModel retrievedUgb = ugbService.getUgbById(idUgb);

        List<UserModel> listPembimbing = ugbService.getListPembimbing();
        // System.out.println("###LIST PEMBIMBING: "+ listPembimbing);
        model.addAttribute("ugb", retrievedUgb);

        model.addAttribute("listPembimbing", listPembimbing);
        model.addAttribute("roleUser", baseService.getCurrentRole());
        return "update-ugb";
    }

    @GetMapping("/ugb/viewall")
    public String listUgb(Model model) {
        System.out.println("*** test ***");

        List<UgbModel> result = ugbService.viewAllUgb();
        
        System.out.println("*** list ugb retrieved: "+ result);

        model.addAttribute("listUgb", result);
        model.addAttribute("roleUser", baseService.getCurrentRole());
        return "viewall-ugb";
    }

    @GetMapping("/ugb/filter")
    public String filterUgb(@RequestParam("status") String status, Model model) {
        System.out.println("MASUK ==");
        List<UgbModel> filteredUGBList = ugbService.filterUgb(status);
        model.addAttribute("listUgb", filteredUGBList);
        model.addAttribute("roleUser", baseService.getCurrentRole());
        return "viewall-ugb";
    }

    @GetMapping("/ugb/detail/{idUgb}")
    public String viewDetailUgb(@PathVariable Long idUgb, Model model) {
        UgbModel retrievedUgb = ugbService.getUgbById(idUgb);
        model.addAttribute("ugb", retrievedUgb);
        model.addAttribute("roleUser", baseService.getCurrentRole());
        return "detail-ugb";
    }

    @GetMapping("/ugb/approve/{idUgb}")
    public String approveUgb(@PathVariable Long idUgb, Model model){
        UgbModel retrievedUgb = ugbService.getUgbById(idUgb);
        // System.out.println(retrievedUgb.getJudulUgb());
        // System.out.println("status before: "+retrievedUgb.getStatusUgb());

        ugbService.approveUgb(retrievedUgb);

        // System.out.println("status after: "+retrievedUgb.getStatusUgb());
        
        model.addAttribute("ugb", retrievedUgb);
        model.addAttribute("roleUser", baseService.getCurrentRole());
        return "detail-ugb";
    }

    @PostMapping("/ugb/deny/{idUgb}")
    public String denyUgb(@PathVariable Long idUgb, @RequestParam("catatan") String catatan, Model model){
        UgbModel retrievedUgb = ugbService.getUgbById(idUgb);
        ugbService.denyUgb(retrievedUgb, catatan);
        model.addAttribute("ugb", retrievedUgb);
        model.addAttribute("roleUser", baseService.getCurrentRole());
        return "detail-ugb";

    }
}
