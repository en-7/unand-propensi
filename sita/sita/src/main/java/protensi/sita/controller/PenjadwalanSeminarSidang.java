package protensi.sita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import protensi.sita.model.JadwalSidangModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import protensi.sita.model.SeminarHasilModel;
import protensi.sita.model.SeminarProposalModel;
import protensi.sita.service.SeminarProposalService;
import protensi.sita.service.jadwalSidangSeminarService;

import java.util.ArrayList;
import java.util.List;


@Controller
public class PenjadwalanSeminarSidang {

    @Autowired
    private jadwalSidangSeminarService jadwalSidangSeminarService;

    @Autowired
    private SeminarProposalService seminarProposalService;


    ///////////////////////////SEMINAR PROPOSAL///////////////////////////

    //method add jadwalsempro
    @GetMapping("/jadwalSidangProposal/create/{id}")
    public String addJadwalSidangProposalForm(@PathVariable("id") Long id, Model model){
        JadwalSidangModel jadwalSidang = new JadwalSidangModel();
        SeminarProposalModel getSeminarProposalById = seminarProposalService.findSemproById(id);
        jadwalSidang.setSeminarProposal(getSeminarProposalById);

        model.addAttribute("addJadwalSempro", jadwalSidang);
        model.addAttribute("getPenguji", jadwalSidang.getSeminarProposal().getUgb().getPenguji());
        return "PenjadwalanSeminarSidang/form-add-jadwalSempro";

    }

    @PostMapping("/jadwalSidangProposal/create")
    public String addJadwalSidangProposalSubmitPage (@ModelAttribute JadwalSidangModel jadwalSidangSeminar, Model model){
        jadwalSidangSeminarService.addJadwalSidangSeminar(jadwalSidangSeminar);
        return "redirect:/jadwalSidangProposal";
    }

    //method viewall JadwalSempro

    @GetMapping("/jadwalSidangProposal")
    public String jadwalSidangProposalForm(Model model){
        List<JadwalSidangModel> listJadwalSidangSeminar = jadwalSidangSeminarService.getListJadwalSidang();
        model.addAttribute("listjadwalSidangSeminar", listJadwalSidangSeminar);
        return "PenjadwalanSeminarSidang/jadwalSidangProposal";
    }

    @GetMapping("/jadwalSidangProposal-pendaftar")
    public String jadwalSidangProposalPendafta(Model model){
        List<SeminarProposalModel> listPendaftarSempro = seminarProposalService.findAllSempro();
        List<SeminarProposalModel> newListPendaftarSempro = new ArrayList<SeminarProposalModel>();
        for(SeminarProposalModel i : listPendaftarSempro){
            if(i.getJadwalSidang() == null){
                newListPendaftarSempro.add(i);
            }
        }
        model.addAttribute("listdaftarSempro", newListPendaftarSempro);

        return "PenjadwalanSeminarSidang/jadwalSidangProposal-pendaftar";
    }

    //controller delete sempro
    @GetMapping(value = "/jadwalSidangProposal/delete/{id}")
    public String deleteSempro(@PathVariable Long id, Model model){
        jadwalSidangSeminarService.deletesJadwalSidangSeminar(id);
        return "PenjadwalanSeminarSidang/deleteSempro";
    }

    //controller update sempro
    @GetMapping("/jadwalSidangProposal/setJadwal/{id}")
    public String SetJadwalSidangProposalFormPage(@PathVariable("id") Long id, Model model){
        JadwalSidangModel setJadwalSempro = jadwalSidangSeminarService.getJadwalSidangById(id);
        model.addAttribute("setJadwalSempro", setJadwalSempro);
        model.addAttribute("getPengujiSet", setJadwalSempro.getSeminarProposal().getUgb().getPenguji());
        return "PenjadwalanSeminarSidang/form-set-jadwalSempro";
    }

    @PostMapping("/jadwalSidangProposal/setJadwal")
    public String SetJadwalSidangProposalSubmitPage(@ModelAttribute JadwalSidangModel jadwalSempro, Model model) {
        jadwalSidangSeminarService.setJadwalSidang(jadwalSempro);
        System.out.println(jadwalSempro.getTanggalSempro().getClass());
        return "redirect:/jadwalSidangProposal";

    }

    ///////////////////////////SEMINAR HASIL///////////////////////////

    //controller viewall jadwal semhas
    @GetMapping("/jadwalSidangHasil")
    public String jadwalSidangHasilForm(Model model){
        List<JadwalSidangModel> listJadwalSidangSeminarHasil = jadwalSidangSeminarService.getListJadwalSidang();
        List<JadwalSidangModel> getListSidangHasil = new ArrayList<>();
        for(JadwalSidangModel i :  listJadwalSidangSeminarHasil){
            if (i.getTanggalSemhas() != null){
                getListSidangHasil.add(i);
            }
        }
        model.addAttribute("listjadwalSidangSeminarHasil", getListSidangHasil);
        return "PenjadwalanSeminarSidang/jadwalSidangHasil";
    }

    //controller delete jadwal semhas
    @GetMapping(value = "/jadwalSidangHasil/delete/{id}")
    public String deleteSemhas(@PathVariable Long id, Model model){
        jadwalSidangSeminarService.deletesJadwalSidangSeminar(id);
        return "PenjadwalanSeminarSidang/deleteSemhas";
    }

    //controller update semhas
    @GetMapping("/jadwalSidangHasil/setJadwal/{id}")
    public String SetJadwalSidangHasilFormPage(@PathVariable("id") Long id, Model model){
        JadwalSidangModel setJadwalSemhas = jadwalSidangSeminarService.getJadwalSidangById(id);
        model.addAttribute("setJadwalSemhas", setJadwalSemhas);
        return "PenjadwalanSeminarSidang/form-set-jadwalSemhas";
    }

    @PostMapping("/jadwalSidangHasil/setJadwal")
    public String SetJadwalSidangHasilSubmitPage(@ModelAttribute JadwalSidangModel jadwalSemhas, Model model) {
        jadwalSidangSeminarService.setJadwalSidang(jadwalSemhas);
        System.out.println(jadwalSemhas.getTanggalSempro());
        return "redirect:/jadwalSidangHasil";

    }


    //SIDANG TUGAS AKHIR///

    //viewall jadwal Sidang Tugas Akhir
    @GetMapping("/jadwalSidangTugasAkhir")
    public String jadwalSidangProposal(Model model){
        List<JadwalSidangModel> listJadwalSidangSeminar = jadwalSidangSeminarService.getListJadwalSidang();
        List<JadwalSidangModel> getListJadwalSidangTA = new ArrayList<>();
        for(JadwalSidangModel i : listJadwalSidangSeminar){
            if (i.getTanggalSidangTa() !=null){
                getListJadwalSidangTA.add(i);
            }
        }
        model.addAttribute("listjadwalSidangTA", getListJadwalSidangTA);
        return "PenjadwalanSeminarSidang/jadwalSidangTA";
    }

    //controller delete sempro
    @GetMapping(value = "/jadwalSidangTugasAkhir/delete/{id}")
    public String deleteSidangTA(@PathVariable Long id, Model model){
        jadwalSidangSeminarService.deletesJadwalSidangSeminar(id);
        return "PenjadwalanSeminarSidang/deleteTA";
    }


    //controller update sidang TA
    @GetMapping("/jadwalSidangTugasAkhir/setJadwal/{id}")
    public String SetJadwalSidangTAFormPage(@PathVariable("id") Long id, Model model){
        JadwalSidangModel setJadwalSidangTA = jadwalSidangSeminarService.getJadwalSidangById(id);
        model.addAttribute("setJadwalSidangTA", setJadwalSidangTA);
        return "PenjadwalanSeminarSidang/form-set-jadwalSidangTA";
    }

    @PostMapping("/jadwalSidangTugasAkhir/setJadwal")
    public String SetJadwalSidangTASubmitPage(@ModelAttribute JadwalSidangModel jadwalSidangTA, Model model) {
        System.out.println(jadwalSidangTA.getTanggalSempro());
        jadwalSidangSeminarService.setJadwalSidang(jadwalSidangTA);
        System.out.println(jadwalSidangTA.getTanggalSempro());
        return "redirect:/jadwalSidangTugasAkhir";

    }



}
