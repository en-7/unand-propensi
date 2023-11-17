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
import org.springframework.util.StringUtils;

import protensi.sita.service.UgbServiceImpl;
import protensi.sita.service.BaseService;
import protensi.sita.service.MahasiswaServiceImpl;
import protensi.sita.security.UserDetailsServiceImpl;
import protensi.sita.service.BaseService;
import protensi.sita.service.SeminarHasilServiceImpl;
import protensi.sita.service.SeminarProposalServiceImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SeminarHasilController {

    @Autowired
    private UgbServiceImpl ugbService;

    @Autowired
    private SeminarProposalServiceImpl seminarProposalService;

    @Autowired
    private MahasiswaServiceImpl mahasiswaService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private SeminarHasilServiceImpl seminarHasilService;

    @Autowired
    public BaseService baseService;

    @GetMapping("/seminar-hasil/add")
    public String addSeminarHasilFormPage(Model model, Authentication authentication) {
        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);
        if (user.getRoles().contains(EnumRole.MAHASISWA)) {
            MahasiswaModel mahasiswa = mahasiswaService.findMahasiswaByUsername(user.getUsername());
            UgbModel ugb = ugbService.findByIdMahasiswa(mahasiswa);
            SeminarProposalModel sempro = seminarProposalService.findSemproByUgb(ugb);
            SeminarHasilModel seminarHasil = seminarHasilService.findSemhasBySempro(sempro);
            if (sempro != null) {
                if (sempro.getStatusDokumen().equals("APPROVED")) {
                    if (seminarHasil != null) {
                        model.addAttribute("roleUser", baseService.getCurrentRole());
                        model.addAttribute("seminarHasil", seminarHasil);
                        return "detail-semhas-mahasiswa";
                    } else {
                        seminarHasil = new SeminarHasilModel();
                        model.addAttribute("roleUser", baseService.getCurrentRole());
                        model.addAttribute("seminarHasil", seminarHasil);
                        return "add-semhas-form";
                    }
                } else {
                    model.addAttribute("roleUser", baseService.getCurrentRole());
                    return "error-semhas";
                }
            } else {
                model.addAttribute("roleUser", baseService.getCurrentRole());
                return "error-semhas";
            }
        } else {
            model.addAttribute("roleUser", baseService.getCurrentRole());
            return "error-semhas";
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

        String namaFileAccPembimbing = StringUtils.cleanPath(acc_pembimbing.getOriginalFilename());
        String namaFileLaporanKp = StringUtils.cleanPath(bukti_kp.getOriginalFilename());
        String namaFileRisalahSempro = StringUtils.cleanPath(risalah_sempro.getOriginalFilename());
        String namaFileCttSempro = StringUtils.cleanPath(notes_sempro.getOriginalFilename());
        String namaFileSaps = StringUtils.cleanPath(form_saps.getOriginalFilename());
        String namaFileDraftTa = StringUtils.cleanPath(draft_TA.getOriginalFilename());

        seminarHasil.setNameFilePersetujuanPembimbing(namaFileAccPembimbing);
        seminarHasil.setNameFileLaporanKp(namaFileLaporanKp);
        seminarHasil.setNameFileRisalahSempro(namaFileRisalahSempro);
        seminarHasil.setNameFileCatatanSempro(namaFileCttSempro);
        seminarHasil.setNameFileSaps(namaFileSaps);
        seminarHasil.setNameFileDraftLaporanTa(namaFileDraftTa);

        seminarHasil.setPersetujuanPembimbing(acc_pembimbing.getBytes());
        seminarHasil.setLaporanKp(bukti_kp.getBytes());
        seminarHasil.setRisalahSempro(risalah_sempro.getBytes());
        seminarHasil.setCatatanSempro(notes_sempro.getBytes());
        seminarHasil.setSaps(form_saps.getBytes());
        seminarHasil.setDraftLaporanTa(draft_TA.getBytes());

        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);
        MahasiswaModel mahasiswa = mahasiswaService.findMahasiswaById(user.getIdUser());
        UgbModel ugb = ugbService.findByIdMahasiswa(mahasiswa);
        SeminarProposalModel sempro = seminarProposalService.findSemproByUgb(ugb);
        seminarHasil.setUgb(ugb);
        seminarHasil.setSeminarProposal(sempro);

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
            @PathVariable Long idSeminarHasil,
            @RequestParam("acc_pembimbing") MultipartFile acc_pembimbing,
            @RequestParam("bukti_kp") MultipartFile bukti_kp,
            @RequestParam("risalah_sempro") MultipartFile risalah_sempro,
            @RequestParam("notes_sempro") MultipartFile notes_sempro,
            @RequestParam("form_saps") MultipartFile form_saps,
            @RequestParam("draft_TA") MultipartFile draft_TA,
            Model model, Authentication authentication) throws IOException {
        try {
            seminarHasil.setPersetujuanPembimbing(acc_pembimbing.getBytes());
            seminarHasil.setLaporanKp(bukti_kp.getBytes());
            seminarHasil.setRisalahSempro(risalah_sempro.getBytes());
            seminarHasil.setCatatanSempro(notes_sempro.getBytes());
            seminarHasil.setSaps(form_saps.getBytes());
            seminarHasil.setDraftLaporanTa(draft_TA.getBytes());

            seminarHasil = seminarHasilService.findSemhasById(idSeminarHasil);

            if (!acc_pembimbing.isEmpty()) {
                String namaFilePersetujuanPembimbing = StringUtils.cleanPath(acc_pembimbing.getOriginalFilename());
                seminarHasil.setNameFilePersetujuanPembimbing(namaFilePersetujuanPembimbing);
                seminarHasil.setPersetujuanPembimbing(namaFilePersetujuanPembimbing.getBytes());
            }

            if (!bukti_kp.isEmpty()) {
                String namaFileBuktiKp = StringUtils.cleanPath(bukti_kp.getOriginalFilename());
                seminarHasil.setNameFileLaporanKp(namaFileBuktiKp);
                seminarHasil.setLaporanKp(namaFileBuktiKp.getBytes());
            }

            if (!risalah_sempro.isEmpty()) {
                String namaFileRisalahSempro = StringUtils.cleanPath(risalah_sempro.getOriginalFilename());
                seminarHasil.setNameFileRisalahSempro(namaFileRisalahSempro);
                seminarHasil.setRisalahSempro(namaFileRisalahSempro.getBytes());
            }

            if (!notes_sempro.isEmpty()) {
                String namaFileNotesSempro = StringUtils.cleanPath(notes_sempro.getOriginalFilename());
                seminarHasil.setNameFileCatatanSempro(namaFileNotesSempro);
                seminarHasil.setCatatanSempro(namaFileNotesSempro.getBytes());
            }

            if (!form_saps.isEmpty()) {
                String namaFileFormSaps = StringUtils.cleanPath(form_saps.getOriginalFilename());
                seminarHasil.setNameFileSaps(namaFileFormSaps);
                seminarHasil.setSaps(namaFileFormSaps.getBytes());
            }

            if (!draft_TA.isEmpty()) {
                String namaFileDraftTA = StringUtils.cleanPath(draft_TA.getOriginalFilename());
                seminarHasil.setNameFileDraftLaporanTa(namaFileDraftTA);
                seminarHasil.setDraftLaporanTa(namaFileDraftTA.getBytes());
            }

            seminarHasil.setCatatan(null);
            seminarHasil.setStatusDokumen("SUBMITTED");
            seminarHasilService.updateSemhas(seminarHasil);
            model.addAttribute("seminarHasil", seminarHasil);
            return "detail-semhas-mahasiswa";
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
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
            return "detail-semhas-koordinator";
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Terjadi error ketika save file.");
        }
    }

    @GetMapping("/seminar-hasil/downloadFile")
    public void downloadFile(@RequestParam("type") String type,
            @RequestParam("id") Long id,
            HttpServletResponse response) {
        try {
            SeminarHasilModel retrievedSemhas = seminarHasilService.findSemhasById(id);
            response.setContentType("application/ocetet-stream");
            String headerKey = "Content-Disposition";

            if (type.equals("FILE PERSETUJUAN PEMBIMBING")) {
                String headerValue = "attachment; filename=" + retrievedSemhas.getNameFilePersetujuanPembimbing();
                response.setHeader(headerKey, headerValue);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(retrievedSemhas.getPersetujuanPembimbing());
                outputStream.close();
            }

            else if (type.equals("FILE TANDA TERIMA LAPORAN KP")) {
                String headerValue = "attachment; filename=" + retrievedSemhas.getNameFileLaporanKp();
                response.setHeader(headerKey, headerValue);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(retrievedSemhas.getLaporanKp());
                outputStream.close();
            }

            else if (type.equals("FILE RISALAH SEMINAR PROPOSAL")) {
                String headerValue = "attachment; filename=" + retrievedSemhas.getNameFileRisalahSempro();
                response.setHeader(headerKey, headerValue);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(retrievedSemhas.getRisalahSempro());
                outputStream.close();
            }

            else if (type.equals("FILE CATATAN SETELAH SEMINAR PROPOSAL")) {
                String headerValue = "attachment; filename=" + retrievedSemhas.getNameFileCatatanSempro();
                response.setHeader(headerKey, headerValue);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(retrievedSemhas.getCatatanSempro());
                outputStream.close();
            }

            else if (type.equals("FILE FORM SAPS")) {
                String headerValue = "attachment; filename=" + retrievedSemhas.getNameFileSaps();
                response.setHeader(headerKey, headerValue);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(retrievedSemhas.getSaps());
                outputStream.close();
            }

            else if (type.equals("FILE DRAFT LAPORAN TA")) {
                String headerValue = "attachment; filename=" + retrievedSemhas.getNameFileDraftLaporanTa();
                response.setHeader(headerKey, headerValue);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(retrievedSemhas.getDraftLaporanTa());
                outputStream.close();
            }
        }

        catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }
}