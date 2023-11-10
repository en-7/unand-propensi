package protensi.sita.controller;

import protensi.sita.model.EnumRole;
import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.UgbModel;
import protensi.sita.model.UserModel;
import protensi.sita.security.UserDetailsServiceImpl;
import protensi.sita.service.MahasiswaServiceImpl;
import protensi.sita.service.SeminarProposalServiceImpl;
import protensi.sita.service.UgbServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


@Resource
@Controller
@RequestMapping("/seminar-proposal")
public class SeminarProposalController {
    @Qualifier("seminarProposalServiceImpl")
    @Autowired
    private SeminarProposalServiceImpl seminarProposalService;

    @Qualifier("ugbServiceImpl")
    @Autowired
    private UgbServiceImpl ugbService;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Qualifier("mahasiswaServiceImpl")
    @Autowired
    private MahasiswaServiceImpl mahasiswaService;

    @GetMapping("/add")
    public String addSemproFormPage(Model model, Authentication authentication) {
        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);
        if (user.getRoles().contains(EnumRole.MAHASISWA)) {
            MahasiswaModel mahasiswa = mahasiswaService.findMahasiswaByUsername(user.getUsername());
            UgbModel ugb = ugbService.findByIdMahasiswa(mahasiswa);
            if (ugb.getStatusDokumen().equals("EVALUATED")){
                SeminarProposalModel seminarProposal = new SeminarProposalModel();
                model.addAttribute("seminarProposal", seminarProposal);
                return "sempro/add-sempro-form";
            } else {
                return "sempro/error-sempro";
            }
        } else{
            return "sempro/error-sempro";
        }

            
    }

    @PostMapping("/add")
    public String addSemproSubmitPage(@ModelAttribute SeminarProposalModel seminarProposal,
            @RequestParam("draftProposalTaFile") MultipartFile draftProposalTaFile,
            @RequestParam("buktiKrsFile") MultipartFile buktiKrsFile,
            @RequestParam("persetujuanPembimbingFile") MultipartFile persetujuanPembimbingFile,
            Model model, Authentication authentication) {
        try {
    
            byte[] draftProposalTaBytes = draftProposalTaFile.getBytes();
            byte[] buktiKrsBytes = buktiKrsFile.getBytes();
            byte[] persetujuanPembimbingBytes = persetujuanPembimbingFile.getBytes();
    
            seminarProposal.setDraftProposalTa(draftProposalTaBytes);
            seminarProposal.setBuktiKrs(buktiKrsBytes);
            seminarProposal.setPersetujuanPembimbing(persetujuanPembimbingBytes);
            // Mengatur idUgb dalam entitas SeminarProposalModel
            String namaUser = authentication.getName();
            UserModel user = userDetailsService.findByUsername(namaUser);
            MahasiswaModel mahasiswa = mahasiswaService.findMahasiswaById(user.getIdUser());
            UgbModel ugb = ugbService.findByIdMahasiswa(mahasiswa);
            seminarProposal.setUgb(ugb);
            // Mengatur tahap mahasiswa menjadi "SEMPRO", dan statusDokumen menjadi "SUBMITTED"
            seminarProposal.getUgb().getMahasiswa().setTahap("SEMPRO");
            seminarProposal.setStatusDokumen("SUBMITTED");

            seminarProposalService.addSempro(seminarProposal);
            model.addAttribute("seminarProposal", seminarProposal);
            return "sempro/detail-sempro";
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }

    @GetMapping("/update/{idSeminarProposal}")
    public String updateSemproFormPage(@PathVariable Long idSeminarProposal, Model model) {
        SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
        model.addAttribute("seminarProposal", seminarProposal);
        return "sempro/update-sempro-form";
    }

    @PostMapping("/update")
    public String updateSemproSubmitPage(@ModelAttribute SeminarProposalModel seminarProposal,
            @RequestParam("draftProposalTaFile") MultipartFile draftProposalTaFile,
            @RequestParam("buktiKrsFile") MultipartFile buktiKrsFile,
            @RequestParam("persetujuanPembimbingFile") MultipartFile persetujuanPembimbingFile,
            Model model) {
        try {
            byte[] draftProposalTaBytes = draftProposalTaFile.getBytes();
            byte[] buktiKrsBytes = buktiKrsFile.getBytes();
            byte[] persetujuanPembimbingBytes = persetujuanPembimbingFile.getBytes();
    
            seminarProposal.setDraftProposalTa(draftProposalTaBytes);
            seminarProposal.setBuktiKrs(buktiKrsBytes);
            seminarProposal.setPersetujuanPembimbing(persetujuanPembimbingBytes);
            
            // Melakukan validasi dan menyimpan perubahan ke database
            seminarProposalService.updateSempro(seminarProposal);
            
            model.addAttribute("id", seminarProposal.getIdSeminarProposal());
            return "sempro/update-sempro-success";
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }    

    @GetMapping("/viewall")
    public String listSempro(Model model, Authentication authentication) {
        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);
        if (user.getRoles().contains(EnumRole.KOORDINATOR)) {
            List<SeminarProposalModel> listSempro = seminarProposalService.findAllSempro();
            model.addAttribute("listSempro", listSempro);
            return "sempro/viewall-sempro";
        } else if (user.getRoles().contains(EnumRole.PEMBIMBING) && user.getRoles().contains(EnumRole.PENGUJI)){
            List<SeminarProposalModel> listSemproPembimbing = seminarProposalService.findAllByPembimbing(user.getIdUser());
            List<SeminarProposalModel> listSemproPenguji = seminarProposalService.findAllByPenguji(user.getIdUser());
            List<SeminarProposalModel> listSempro = new ArrayList<SeminarProposalModel>();
            listSempro.addAll(listSemproPembimbing);
            listSempro.addAll(listSemproPenguji);
            model.addAttribute("listSempro", listSempro);
            return "sempro/viewall-sempro-dosen";
        }
        return "sempro/error-sempro";
    }

    @GetMapping("/filter")
    public String filterSeminarProposals(@RequestParam String status, Model model) {
        List<SeminarProposalModel> filteredProposals = seminarProposalService.findSemproByStatusDokumen(status);
        model.addAttribute("listSempro", filteredProposals);
        return "sempro/viewall-sempro";
    }

    @PostMapping("/input-nilai/{idSeminarProposal}")
    public String inputNilai(@PathVariable Long idSeminarProposal, @RequestBody Map<String, Object> data, Model model) {
        SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
        Long nilai = ((Integer) data.get("nilai")).longValue();            
        String statusSempro = (String) data.get("statusSeminarProposal");
        SeminarProposalModel updatedSeminarProposal = seminarProposalService.saveNilaiAndStatus(idSeminarProposal, nilai, statusSempro);
        
        LocalDateTime currentTime = LocalDateTime.now();
        seminarProposal.setTanggalLulus(currentTime);
        seminarProposalService.updateSempro(seminarProposal);
    
        if (updatedSeminarProposal != null) {
            model.addAttribute("seminarProposal", seminarProposal);
            return "sempro/detail-sempro-koordinator";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
        
    }
    

    @PostMapping("/update-nilai/{idSeminarProposal}")
    public String updateNilai(@PathVariable Long idSeminarProposal, @RequestBody Map<String, Object> data, Model model) {
        SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
        Long nilai = ((Integer) data.get("nilai")).longValue();
        String statusSempro = (String) data.get("statusSeminarProposal");
        SeminarProposalModel updatedSeminarProposal = seminarProposalService.saveNilaiAndStatus(idSeminarProposal, nilai, statusSempro);
        
        seminarProposalService.updateSempro(seminarProposal);
        if (updatedSeminarProposal != null) {
            model.addAttribute("seminarProposal", seminarProposal);
            return "sempro/detail-sempro-koordinator";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }
  
    @GetMapping("/detail/{idSeminarProposal}")
    public String viewDetailSemproPage(@PathVariable Long idSeminarProposal, Model model, Authentication authentication) {
        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);
        SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
        model.addAttribute("seminarProposal", seminarProposal);
        if (user.getRoles().contains(EnumRole.KOORDINATOR)) {
            return "sempro/detail-sempro-koordinator";
        } else if (user.getRoles().contains(EnumRole.PEMBIMBING) && user.getRoles().contains(EnumRole.PENGUJI)){
            return "sempro/detail-sempro-dosen";
        }else{
            return "sempro/detail-sempro-mahasiswa";
        }
    }
    
    @GetMapping("/approve/{idSeminarProposal}")
    public String approveSeminarProposal(@PathVariable Long idSeminarProposal, Model model) {
        try {
            SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
            seminarProposal.setStatusDokumen("APPROVED");
            seminarProposalService.updateSempro(seminarProposal);

            model.addAttribute("seminarProposal", seminarProposal);
            return "sempro/detail-sempro-koordinator";
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }

    @PostMapping("/deny/{idSeminarProposal}")
    public String denySeminarProposal(@PathVariable Long idSeminarProposal, @RequestParam("catatan") String catatan, Model model) {
        try {
            SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
            seminarProposal.setStatusDokumen("DENY");
            seminarProposal.setCatatan(catatan);
            seminarProposalService.updateSempro(seminarProposal);

            model.addAttribute("seminarProposal", seminarProposal);
            return "sempro/detail-sempro-koordinator";
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }
}
