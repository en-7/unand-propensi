package protensi.sita.controller;
import protensi.sita.model.SeminarProposalModel;
import protensi.sita.service.SeminarProposalServiceImpl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
public class SeminarProposalController {

    @Qualifier("seminarProposalServiceImpl")
    @Autowired
    private SeminarProposalServiceImpl seminarProposalService;

    @GetMapping("/seminar-proposal/add")
    public String addSemproFormPage(Model model) {
        SeminarProposalModel seminarProposal = new SeminarProposalModel();
        model.addAttribute("seminarProposal", seminarProposal);
        return "add-sempro-form";
    }

    @PostMapping("/seminar-proposal/add")
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

            // Mengatur data file dalam entitas SeminarProposalModel
            seminarProposal.setDraftProposalTa(draftProposalTaBytes);
            seminarProposal.setBuktiKrs(buktiKrsBytes);
            seminarProposal.setPersetujuanPembimbing(persetujuanPembimbingBytes);
            
            // Mengatur idUgb dalam entitas SeminarProposalModel
            /*
                - ambil nama mahasiswa yang login ke sistem
                - ambil id dari nama mahasiswa tersebut
                - cek apakah ugb.mahasiswa ada yang id nya tersebut
                - jika ada ambil idUgb nya
                - set pada seminarProposal.setUgb("idUgb")
            */
            // Mengatur tahap mahasiswa menjadi "SEMPRO"
            //seminarProposal.getUgb().getMahasiswa().setTahap("SEMPRO");

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

    @GetMapping("/seminar-proposal/update/{idSeminarProposal}")
    public String updateSemproFormPage(@PathVariable Long idSeminarProposal, Model model) {
        SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
        model.addAttribute("seminarProposal", seminarProposal);
        return "update-sempro-form";
    }

    @PostMapping("/seminar-proposal/update")
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

            // Mengatur data file dalam entitas SeminarProposalModel
            seminarProposal.setDraftProposalTa(draftProposalTaBytes);
            seminarProposal.setBuktiKrs(buktiKrsBytes);
            seminarProposal.setPersetujuanPembimbing(persetujuanPembimbingBytes);
            
            seminarProposalService.updateSempro(seminarProposal);
            model.addAttribute("id", seminarProposal.getIdSeminarProposal());
            return "update-sempro-success";
        } catch (IOException e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }

    @GetMapping("/seminar-proposal/viewall")
    public String listSempro(Model model) {
        List<SeminarProposalModel> listSempro = seminarProposalService.findAllSempro();
        model.addAttribute("listSempro", listSempro);
        return "viewall-sempro";
    }
    
    @GetMapping("/seminar-proposal/input-nilai/{idSeminarProposal}")
    public String inputNilaiFormPage(@PathVariable Long idSeminarProposal, Model model) {
        SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
        model.addAttribute("seminarProposal", seminarProposal);
        return "input-nilai-sempro-form";
    }

    @PostMapping("/seminar-proposal/input-nilai/{idSeminarProposal}")
    public String inputNilaiSubmotPage(
        @ModelAttribute SeminarProposalModel seminarProposal,
        @RequestParam("nilai") Long nilai, 
        @RequestParam("status") String status, Model model) {
        
        seminarProposal.setNilai(nilai);
        seminarProposal.setStatusSeminarProposal(status);
        // Setelah data disimpan, Anda dapat mengembalikan tampilan yang sesuai.
        return "redirect:/seminar-proposal/viewall";
    }

    
    @GetMapping("/seminar-proposal/detail/{idSeminarProposal}")
    public String viewDetailSemproPage(@PathVariable Long idSeminarProposal, Model model) {
        SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
        model.addAttribute("seminarProposal", seminarProposal);
        return "detail-sempro";

    }

    @GetMapping("/seminar-proposal/download/{idSeminarProposal}/{documentType}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long idSeminarProposal, @PathVariable String documentType) {
        SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
    
        byte[] documentData = null;
        String fileName = "DraftProposalTA.pdf";
    
        if ("draftProposalTa".equals(documentType)) {
            documentData = seminarProposal.getDraftProposalTa();
        }
    
        if (documentData != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", fileName);
    
            return new ResponseEntity<>(documentData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/seminar-proposal/preview/{idSeminarProposal}/{documentType}")
    public ResponseEntity<byte[]> previewDocument(@PathVariable Long idSeminarProposal, @PathVariable String documentType) {
        SeminarProposalModel seminarProposal = seminarProposalService.findSemproById(idSeminarProposal);
    
        byte[] documentData = null;
    
        if ("draftProposalTa".equals(documentType)) {
            documentData = seminarProposal.getDraftProposalTa();
        }
    
        if (documentData != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
    
            return new ResponseEntity<>(documentData, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /* 
    @GetMapping("/mitra/update/{idMitra}")
    public String updateMitraFormPage(@PathVariable Long idMitra, Model model, Authentication authentication) {
        try {
            MitraModel mitra = mitraService.findMitraById(idMitra);
            model.addAttribute("mitra", mitra);
            log.info(authentication.getName() + " accessed updateMitraFormPage.");
            return "update-mitra-form";
        } catch (NoSuchElementException e) {
            log.info("Mitra with id: " + idMitra + " not found.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Mitra with id: " + idMitra + " not found.");
        }

    }

    @PostMapping("/mitra/update")
    public String updateMitraSubmitPage(@ModelAttribute MitraModel mitra, Model model, BindingResult bindingResult,
            Authentication authentication) {
        if (bindingResult.hasFieldErrors()) {
            log.info("Model has invalid type or missing field when updating Mitra.");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Model Attribute has invalid type or missing field.");
        } else {
            MitraModel updateMitra = mitraService.updateMitra(mitra);
            model.addAttribute("nama", updateMitra.getNama());
            log.info(authentication.getName() + " updated new mitra: " + mitra.getNama() + ".");
            return "update-mitra-success";
        }
    }
    */

}
