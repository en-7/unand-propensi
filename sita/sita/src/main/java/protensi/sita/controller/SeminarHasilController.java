package protensi.sita.controller;

import protensi.sita.model.SeminarHasilModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import protensi.sita.service.SeminarHasilServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class SeminarHasilController {

    @Autowired
    private MahasiswaServiceImpl mahasiswaService;

    @Autowired
    private ManageUserServiceImpl userService;

    @Autowired
    private SeminarHasilServiceImpl seminarHasilService;

    @GetMapping("/seminar-hasil/add")
    public String addSeminarHasilFormPage(Model model) {
        SeminarHasilModel seminarHasilModel = new SeminarHasilModel();

        model.addAttribute("ugb", seminarHasilModel);

        return "add-semhas-form";
    }

    @PostMapping("/seminar-hasil/add")
    public String addSeminarHasilSubmitPage(@ModelAttribute SeminarHasilModel seminarHasil,
            @RequestParam("acc_pembimbing") MultipartFile acc_pembimbing,
            @RequestParam("bukti_kp") MultipartFile bukti_kp,
            @RequestParam("risalah_sempro") MultipartFile risalah_sempro,
            @RequestParam("notes_sempro") MultipartFile notes_sempro,
            @RequestParam("form_saps") MultipartFile form_saps,
            @RequestParam("draft_TA") MultipartFile draft_TA,
            Model model) throws IOException {
        seminarHasil.setPersetujuanPembimbing(acc_pembimbing.getBytes());
        seminarHasil.setLaporanKP(bukti_kp.getBytes());
        seminarHasil.setRisalahSempro(risalah_sempro.getBytes());
        seminarHasil.setCatatanSempro(notes_sempro.getBytes());
        seminarHasil.setSaps(form_saps.getBytes());
        seminarHasil.setDraftLaporanTa(draft_TA.getBytes());
        seminarHasil.setStatusDokumen("SUBMITTED");
        seminarHasilService.addSeminarHasil(seminarHasil);

        return "add-semhas-success";
    }

    @GetMapping("/seminar-hasil/viewall")
    public String listSeminarHasil(Model model) {
        List<SeminarHasilModel> listSeminarHasil = seminarHasilService.findAllSeminarHasil();
        model.addAttribute("listSeminarHasil", listSeminarHasil);
        return "viewall-semhas";
    }

}
