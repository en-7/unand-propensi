package protensi.sita.controller;

import protensi.sita.model.SeminarProposalModel;
import protensi.sita.service.SeminarProposalServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.time.LocalDateTime;
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

    @GetMapping("/add")
    public String addSemproFormPage(Model model) {
        SeminarProposalModel seminarProposal = new SeminarProposalModel();
        model.addAttribute("seminarProposal", seminarProposal);
        return "add-sempro-form";
    }

    @PostMapping("/add")
    public String addSemproSubmitPage(@ModelAttribute SeminarProposalModel seminarProposal,
            @RequestParam("draftProposalTaFile") MultipartFile draftProposalTaFile,
            @RequestParam("buktiKrsFile") MultipartFile buktiKrsFile,
            @RequestParam("persetujuanPembimbingFile") MultipartFile persetujuanPembimbingFile,
            Model model) {
        try {
            // Mengambil data file yang diunggah
            byte[] draftProposalTaBytes = draftProposalTaFile.getBytes();
            byte[] buktiKrsBytes = buktiKrsFile.getBytes();
            byte[] persetujuanPembimbingBytes = persetujuanPembimbingFile.getBytes();
    
            seminarProposal.setDraftProposalTa(draftProposalTaBytes);
            seminarProposal.setBuktiKrs(buktiKrsBytes);
            seminarProposal.setPersetujuanPembimbing(persetujuanPembimbingBytes);

            // Mengatur idUgb dalam entitas SeminarProposalModel
            /*
             * - ambil nama mahasiswa yang login ke sistem
             * - ambil id dari nama mahasiswa tersebut
             * - cek apakah ugb.mahasiswa ada yang id nya tersebut
             * - jika ada ambil idUgb nya
             * - set pada seminarProposal.setUgb("idUgb")
             */
            // Mengatur tahap mahasiswa menjadi "SEMPRO"
            // seminarProposal.getUgb().getMahasiswa().setTahap("SEMPRO");
            seminarProposal.setStatusDokumen("SUBMITTED");

            seminarProposalService.addSempro(seminarProposal);

            // jika ugb sudah diset, maka
            /*
            model.addAttribute("seminarProposal", seminarProposal);
            return "detail-sempro";
            */

            // karena ugb blm bisa diset, maka sementara
            model.addAttribute("id", seminarProposal.getIdSeminarProposal());
            return "add-sempro-success";
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }

    @GetMapping("/update/{idSeminarProposal}")
    public String updateSemproFormPage(@PathVariable Long idSeminarProposal, Model model) {
        SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
        model.addAttribute("seminarProposal", seminarProposal);
        return "update-sempro-form";
    }

    @PostMapping("/update")
    public String updateSemproSubmitPage(@ModelAttribute SeminarProposalModel seminarProposal,
            @RequestParam("draftProposalTaFile") MultipartFile draftProposalTaFile,
            @RequestParam("buktiKrsFile") MultipartFile buktiKrsFile,
            @RequestParam("persetujuanPembimbingFile") MultipartFile persetujuanPembimbingFile,
            Model model) {
        try {
            // Mengambil data file yang diunggah
            byte[] draftProposalTaBytes = draftProposalTaFile.getBytes();
            byte[] buktiKrsBytes = buktiKrsFile.getBytes();
            byte[] persetujuanPembimbingBytes = persetujuanPembimbingFile.getBytes();
    
            seminarProposal.setDraftProposalTa(draftProposalTaBytes);
            seminarProposal.setBuktiKrs(buktiKrsBytes);
            seminarProposal.setPersetujuanPembimbing(persetujuanPembimbingBytes);
            
            // Melakukan validasi dan menyimpan perubahan ke database
            seminarProposalService.updateSempro(seminarProposal);
            
            model.addAttribute("id", seminarProposal.getIdSeminarProposal());
            return "update-sempro-success";
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }    

    @GetMapping("/viewall")
    public String listSempro(Model model) {
        List<SeminarProposalModel> listSempro = seminarProposalService.findAllSempro();
        model.addAttribute("listSempro", listSempro);
        return "viewall-sempro";
    }

    @GetMapping("/filter")
    public String filterSeminarProposals(@RequestParam String status, Model model) {
        List<SeminarProposalModel> filteredProposals = seminarProposalService.findSemproByStatusDokumen(status);
        model.addAttribute("listSempro", filteredProposals);
        return "viewall-sempro";
    }

    @PostMapping("/input-nilai/{idSeminarProposal}")
    public String inputNilai(@PathVariable Long idSeminarProposal, @RequestBody Map<String, Object> data) {
        SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
        Long nilai = ((Integer) data.get("nilai")).longValue();            
        String statusSempro = (String) data.get("statusSeminarProposal");

        SeminarProposalModel updatedSeminarProposal = seminarProposalService.saveNilaiAndStatus(idSeminarProposal, nilai, statusSempro);
        LocalDateTime currentTime = LocalDateTime.now();
        seminarProposal.setTanggalLulus(currentTime);
        seminarProposalService.updateSempro(seminarProposal);
        if (updatedSeminarProposal != null) {
            return "detail-sempro";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }

    @PostMapping("/update-nilai/{idSeminarProposal}")
    public String updateNilai(@PathVariable Long idSeminarProposal, @RequestBody Map<String, Object> data) {
        SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
        Long nilai = ((Integer) data.get("nilai")).longValue();
        String statusSempro = (String) data.get("statusSeminarProposal");
        SeminarProposalModel updatedSeminarProposal = seminarProposalService.saveNilaiAndStatus(idSeminarProposal, nilai, statusSempro);
        seminarProposalService.updateSempro(seminarProposal);
        if (updatedSeminarProposal != null) {
            return "detail-sempro";
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }
  
    @GetMapping("/detail/{idSeminarProposal}")
    public String viewDetailSemproPage(@PathVariable Long idSeminarProposal, Model model) {
        SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
        model.addAttribute("seminarProposal", seminarProposal);
        return "detail-sempro";
    }
    
    @PostMapping("/approve/{idSeminarProposal}")
    public String approveSeminarProposal(@PathVariable Long idSeminarProposal, Model model) {
        try {
            // Temukan SeminarProposalModel berdasarkan ID
            SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);

            // Ubah status dokumen menjadi "APPROVED"
            seminarProposal.setStatusDokumen("APPROVED");
            // Simpan perubahan ke database
            seminarProposalService.updateSempro(seminarProposal);

            model.addAttribute("seminarProposal", seminarProposal);
            return "detail-sempro";
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }

    @PostMapping("/deny/{idSeminarProposal}")
    public String denySeminarProposal(@PathVariable Long idSeminarProposal, @RequestParam("catatan") String catatan, Model model) {
        try {
            // Temukan SeminarProposalModel berdasarkan ID
            SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);

            // Ubah status dokumen menjadi "APPROVED"
            seminarProposal.setStatusDokumen("DENY");
            seminarProposal.setCatatan(catatan);

            // Simpan perubahan ke database
            seminarProposalService.updateSempro(seminarProposal);

            model.addAttribute("seminarProposal", seminarProposal);
            return "detail-sempro";
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }
    
    @GetMapping("/downloadFile/{idSeminarProposal}/{fileType}")
@ResponseBody
public ResponseEntity<Resource> downloadFile(@PathVariable Long idSeminarProposal, @PathVariable String fileType) {
    // Dapatkan file dari service berdasarkan tipe file
    MultipartFile file = seminarProposalService.getFileByIdAndType(idSeminarProposal, fileType);

    // Periksa file dan siapkan sebagai Resource
    if (file != null) {
        try {
            byte[] fileBytes = file.getBytes();
            Resource resource = (Resource) new ByteArrayResource(fileBytes);

            // Kembalikan file sebagai tautan unduhan
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getOriginalFilename())
                .body(resource);
        } catch (IOException e) {
            // Tangani kesalahan IO jika terjadi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    } else {
        // Tindakan yang sesuai jika file tidak ditemukan
        return ResponseEntity.notFound().build();
    }
}
}
