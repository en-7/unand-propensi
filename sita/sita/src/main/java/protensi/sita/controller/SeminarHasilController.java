package protensi.sita.controller;

import protensi.sita.model.UserModel;
import protensi.sita.model.EnumRole;
import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.SeminarHasilModel;
import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.UgbModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.UserDetailsAwareConfigurer;
import org.springframework.security.core.Authentication;
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

import protensi.sita.service.UgbServiceImpl;
import protensi.sita.service.MahasiswaServiceImpl;
import protensi.sita.security.UserDetailsServiceImpl;
import protensi.sita.service.SeminarHasilServiceImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class SeminarHasilController {

    @Autowired
    private UgbServiceImpl ugbService;

    @Autowired
    private MahasiswaServiceImpl mahasiswaService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private SeminarHasilServiceImpl seminarHasilService;

    @GetMapping("/seminar-hasil/add")
    public String addSeminarHasilFormPage(Model model, Authentication authentication) {
        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);
        if (user.getRoles().contains(EnumRole.MAHASISWA)) {
            MahasiswaModel mahasiswa = mahasiswaService.findMahasiswaByUsername(user.getUsername());
            UgbModel ugb = ugbService.findByIdMahasiswa(mahasiswa);
            if (ugb.getStatusDokumen().equals("EVALUATED")) {
                SeminarHasilModel seminarHasil = new SeminarHasilModel();
                model.addAttribute("seminarHasil", seminarHasil);
                return "add-semhas-form";
            } else {
                return "error";
            }
        }

        else {
            return "error";
        }
    }

    @PostMapping("/seminar-hasil/add")
    public String addSeminarHasilSubmitPage(@ModelAttribute SeminarHasilModel seminarHasil,
            @RequestParam("acc_pembimbing") MultipartFile acc_pembimbing,
            @RequestParam("bukti_kp") MultipartFile bukti_kp,
            @RequestParam("risalah_sempro") MultipartFile risalah_sempro,
            @RequestParam("notes_sempro") MultipartFile notes_sempro,
            @RequestParam("form_saps") MultipartFile form_saps,
            @RequestParam("draft_TA") MultipartFile draft_TA,
            Model model, Authentication authentication) throws IOException {
        seminarHasil.setPersetujuanPembimbing(acc_pembimbing.getBytes());
        seminarHasil.setLaporanKP(bukti_kp.getBytes());
        seminarHasil.setRisalahSempro(risalah_sempro.getBytes());
        seminarHasil.setCatatanSempro(notes_sempro.getBytes());
        seminarHasil.setSaps(form_saps.getBytes());
        seminarHasil.setDraftLaporanTa(draft_TA.getBytes());

        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);
        MahasiswaModel mahasiswa = mahasiswaService.findMahasiswaById(user.getIdUser());
        UgbModel ugb = ugbService.findByIdMahasiswa(mahasiswa);
        seminarHasil.setUgb(ugb);

        seminarHasil.getUgb().getMahasiswa().setTahap("SEMHAS");
        seminarHasil.setStatusDokumen("SUBMITTED");

        seminarHasilService.addSeminarHasil(seminarHasil);
        model.addAttribute("seminarHasil", seminarHasil);
        return "detail-semhas-mahasiswa";
    }

    @GetMapping("/seminar-hasil/viewall")
    public String listSeminarHasil(Model model, Authentication authentication) {
        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);

        if (user.getRoles().contains(EnumRole.KOORDINATOR)) {
            List<SeminarHasilModel> listSeminarHasil = seminarHasilService.findAllSeminarHasil();
            model.addAttribute("listSeminarHasil", listSeminarHasil);
            return "viewall-semhas";
        } else if (user.getRoles().contains(EnumRole.PEMBIMBING) && user.getRoles().contains(EnumRole.PENGUJI)) {
            List<SeminarHasilModel> listSemhasPembimbing = seminarHasilService.findAllByPembimbing(user.getIdUser());
            List<SeminarHasilModel> listSemhasPenguji = seminarHasilService.findAllByPenguji(user.getIdUser());
            List<SeminarHasilModel> listSemhas = new ArrayList<SeminarHasilModel>();
            listSemhas.addAll(listSemhasPembimbing);
            listSemhas.addAll(listSemhasPenguji);
            model.addAttribute("listSemhas", listSemhas);
            return "viewall-semhas-dosen";
        } else {
            return "error-semhas";
        }
    }

    @GetMapping("/seminar-hasil/detail/{idSeminarHasil}")
    public String viewDetailSemhasPage(@PathVariable Long idSeminarHasil, Model model,
            Authentication authentication) {
        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);
        SeminarHasilModel seminarHasil = seminarHasilService.findSemhasById(idSeminarHasil);
        model.addAttribute("seminarHasil", seminarHasil);
        if (user.getRoles().contains(EnumRole.KOORDINATOR)) {
            return "detail-semhas-koordinator";
        } else if (user.getRoles().contains(EnumRole.PEMBIMBING) && user.getRoles().contains(EnumRole.PENGUJI)) {
            return "detail-semhas-dosen";
        } else {
            return "detail-semhas-mahasiswa";
        }
    }

    @GetMapping("/seminar-hasil/update/{idSeminarHasil}")
    public String updateSemhasFormPage(@PathVariable Long idSeminarHasil, Model model) {
        SeminarHasilModel seminarHasil = seminarHasilService.findSemhasById(idSeminarHasil);
        model.addAttribute("seminarHasil", seminarHasil);
        return "update-semhas-form";
    }

    @PostMapping("/seminar-hasil/update")
    public String updateSeminarHasilSubmitPage(@ModelAttribute SeminarHasilModel seminarHasil,
            @RequestParam("acc_pembimbing") MultipartFile acc_pembimbing,
            @RequestParam("bukti_kp") MultipartFile bukti_kp,
            @RequestParam("risalah_sempro") MultipartFile risalah_sempro,
            @RequestParam("notes_sempro") MultipartFile notes_sempro,
            @RequestParam("form_saps") MultipartFile form_saps,
            @RequestParam("draft_TA") MultipartFile draft_TA,
            Model model, Authentication authentication) throws IOException {
        seminarHasil.setPersetujuanPembimbing(acc_pembimbing.getBytes());
        seminarHasil.setLaporanKP(bukti_kp.getBytes());
        seminarHasil.setRisalahSempro(risalah_sempro.getBytes());
        seminarHasil.setCatatanSempro(notes_sempro.getBytes());
        seminarHasil.setSaps(form_saps.getBytes());
        seminarHasil.setDraftLaporanTa(draft_TA.getBytes());

        seminarHasilService.updateSemhas(seminarHasil);
        model.addAttribute("id", seminarHasil.getIdSeminarHasil());

        return "update-semhas-success";
    }

    @GetMapping("/seminar-hasil/filter")
    public String filterSeminarHasil(@RequestParam String status, Model model) {
        List<SeminarHasilModel> filteredSemhas = seminarHasilService.findSemhasByStatusDokumen(status);
        model.addAttribute("listSemhas", filteredSemhas);
        return "viewall-semhas";
    }

    @PostMapping("/seminar-hasil/input-nilai/{idSeminarHasil}")
    public String inputNilaiSemhas(@PathVariable Long idSeminarHasil, @RequestBody Map<String, Object> data,
            Model model) {
        System.out.println("--- nilai :" + (String) data.get("nilai"));

        SeminarHasilModel seminarHasil = seminarHasilService.findSemhasById(idSeminarHasil);
        Long nilai = ((Integer) data.get("nilai")).longValue();

        // Long nilai = ((Integer) data.get("nilai")).longValue();
        System.out.println("--- parsed nilai :" + nilai);

        // Long nilai = Long.parseLong((String) data.get("nilai"));
        // Long nilai = (Long) data.get("nilai");
        String statusSemhas = (String) data.get("statusSemhas");
        SeminarHasilModel updatedSeminarHasil = seminarHasilService.saveNilaiAndStatus(idSeminarHasil,
                nilai, statusSemhas);

        System.out.println("--- updated semhas :" + updatedSeminarHasil);
        LocalDateTime nowTime = LocalDateTime.now();
        seminarHasil.setTanggalLulus(nowTime);
        seminarHasilService.updateSemhas(seminarHasil);

        if (updatedSeminarHasil != null) {
            model.addAttribute("seminarHasil", seminarHasil);
            return "detail-semhas-koordinator";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Terjadi error ketika save file.");
        }

    }

    @PostMapping("/seminar-hasil/update-nilai/{idSeminarHasil}")
    public String updateNilaiSemhas(@PathVariable Long idSeminarHasil, @RequestBody Map<String, Object> data,
            Model model) {
        SeminarHasilModel seminarHasil = seminarHasilService.findSemhasById(idSeminarHasil);
        Long nilai = ((Integer) data.get("nilai")).longValue();
        String statusSemhas = (String) data.get("statusSemhas");
        SeminarHasilModel updatedSeminarHasil = seminarHasilService.saveNilaiAndStatus(idSeminarHasil,
                nilai, statusSemhas);

        seminarHasilService.updateSemhas(seminarHasil);
        if (updatedSeminarHasil != null) {
            model.addAttribute("seminarHasil", seminarHasil);
            return "detail-semhas-koordinator";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Terjadi error ketika save file.");
        }
    }

    @GetMapping("/seminar-hasil/approve/{idSeminarHasil}")
    public String approveSeminarHasil(@PathVariable Long idSeminarHasil, Model model) {
        try {
            SeminarHasilModel seminarHasil = seminarHasilService.findSemhasById(idSeminarHasil);
            seminarHasil.setStatusDokumen("APPROVED");
            seminarHasilService.updateSemhas(seminarHasil);

            model.addAttribute("seminarHasil", seminarHasil);
            return "detail-semhas-koordinator";
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Terjadi error ketika save file.");
        }
    }

    @PostMapping("/seminar-hasil/deny/{idSeminarHasil}")
    public String denySeminarHasil(@PathVariable Long idSeminarHasil, @RequestParam("catatan") String catatan,
            Model model) {
        try {
            SeminarHasilModel seminarHasil = seminarHasilService.findSemhasById(idSeminarHasil);
            seminarHasil.setStatusDokumen("DENY");
            seminarHasil.setCatatan(catatan);
            seminarHasilService.updateSemhas(seminarHasil);

            model.addAttribute("seminarHasil", seminarHasil);
            return "detail-sempro-koordinator";
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Terjadi error ketika save file.");
        }
    }
}
