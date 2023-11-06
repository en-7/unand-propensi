package protensi.sita.controller;

import protensi.sita.model.TugasAkhirModel;
import protensi.sita.service.TugasAkhirServiceImpl;

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

import java.io.IOException;
import java.util.List;
//import java.util.Map;

@Controller
public class TugasAkhirController {

    @Qualifier("tugasAkhirServiceImpl")
    @Autowired
    private TugasAkhirServiceImpl tugasAkhirService;

    @GetMapping("/tugas-akhir/add")
    public String addSidangTAFormPage(Model model) {
        TugasAkhirModel tugasAkhir = new TugasAkhirModel();
        model.addAttribute("tugasAkhir", tugasAkhir);
        return "add-ta-form";
    }

    @PostMapping("/tugas-akhir/add")
    public String addSidangTASubmitPage(@ModelAttribute TugasAkhirModel tugasAkhir,
            @RequestParam("risalahSemhasFile") MultipartFile risalahSemhasFile,
            @RequestParam("krsPengambilanTaFile") MultipartFile krsPengambilanTaFile,
            @RequestParam("suratPersetujuanSidangFile") MultipartFile suratPersetujuanSidangFile,
            @RequestParam("buktiNilaiKpFile") MultipartFile buktiNilaiKpFile,
            @RequestParam("buktiLembarAsistensiFile") MultipartFile buktiLembarAsistensiFile,
            @RequestParam("lembarKonversiNilaiFile") MultipartFile lembarKonversiNilaiFile,
            @RequestParam("perbaikanLaporanTaFile") MultipartFile perbaikanLaporanTaFile,
            @RequestParam("suratBebasLabFile") MultipartFile suratBebasLabFile,
            @RequestParam("kartuMengikutiSeminarFile") MultipartFile kartuMengikutiSeminarFile,
            @RequestParam("draftLaporanTAFile") MultipartFile draftLaporanTAFile,
            @RequestParam("buktiToeflFile") MultipartFile buktiToeflFile,
            @RequestParam("transkripNilaiTerbaruFile") MultipartFile transkripNilaiTerbaruFile,
            Model model) {
        try {
            // Mengambil data file yang diunggah
            byte[] risalahSemhasBytes = risalahSemhasFile.getBytes();
            byte[] krsPengambilanTaBytes = krsPengambilanTaFile.getBytes();
            byte[] suratPersetujuanSidangBytes = suratPersetujuanSidangFile.getBytes();
            byte[] buktiNilaiKpBytes = buktiNilaiKpFile.getBytes();
            byte[] buktiLembarAsistensiBytes = buktiLembarAsistensiFile.getBytes();
            byte[] lembarKonversiNilaiBytes = lembarKonversiNilaiFile.getBytes();
            byte[] perbaikanLaporanTaBytes = perbaikanLaporanTaFile.getBytes();
            byte[] suratBebasLabBytes = suratBebasLabFile.getBytes();
            byte[] kartuMengikutiSeminarBytes = kartuMengikutiSeminarFile.getBytes();
            byte[] draftLaporanTABytes = draftLaporanTAFile.getBytes();
            byte[] buktiToeflBytes = buktiToeflFile.getBytes();
            byte[] transkripNilaiTerbaruBytes = transkripNilaiTerbaruFile.getBytes();

            // Mengatur data file dalam entitas TugasAkhirModel
            tugasAkhir.setRisalahSemhas(risalahSemhasBytes);
            tugasAkhir.setKrsPengambilanTa(krsPengambilanTaBytes);
            tugasAkhir.setSuratPersetujuanSidang(suratPersetujuanSidangBytes);
            tugasAkhir.setBuktiNilaiKp(buktiNilaiKpBytes);
            tugasAkhir.setBuktiLembarAsistensi(buktiLembarAsistensiBytes);
            tugasAkhir.setLembarKonversiNilai(lembarKonversiNilaiBytes);
            tugasAkhir.setPerbaikanLaporanTa(perbaikanLaporanTaBytes);
            tugasAkhir.setSuratBebasLab(suratBebasLabBytes);
            tugasAkhir.setKartuMengikutiSeminar(kartuMengikutiSeminarBytes);
            tugasAkhir.setDraftLaporanTA(draftLaporanTABytes);
            tugasAkhir.setBuktiToefl(buktiToeflBytes);
            tugasAkhir.setTranskripNilaiTerbaru(transkripNilaiTerbaruBytes);

            tugasAkhir.setStatusDokumen("SUBMITTED");

            tugasAkhirService.addSidangTA(tugasAkhir);
            model.addAttribute("id", tugasAkhir.getIdTugasAkhir());
            return "add-ta-success";
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving the file.");
        }
    }

    @GetMapping("/tugas-akhir/viewall")
    public String listTugasAkhir(Model model) {
        List<TugasAkhirModel> listTugasAkhir = tugasAkhirService.findAllTugasAkhir();
        model.addAttribute("listTugasAkhir", listTugasAkhir);
        return "viewall-ta";
    }
}