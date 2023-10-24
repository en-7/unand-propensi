package protensi.sita.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner;
import org.springframework.web.bind.annotation.*;
import protensi.sita.model.JadwalSidangModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import protensi.sita.service.jadwalSidangSeminarService;

import java.util.List;


@Controller
public class SeminarProposalController {

//    @Qualifier("jadwalSidangSeminarServiceImpl")
    @Autowired
    private jadwalSidangSeminarService jadwalSidangSeminarService;

    @GetMapping("/jadwalSidangProposal")
    public String jadwalSidangProposalForm(Model model){
        List<JadwalSidangModel> listJadwalSidangSeminar = jadwalSidangSeminarService.getListJadwalSidang();
        model.addAttribute("listjadwalSidangSeminar", listJadwalSidangSeminar);
        return "jadwalSidangProposal";
    }

    //controller delete sempro
    @GetMapping(value = "/jadwalSidangProposal/delete/{id}")
    public String deleteSempro(@PathVariable Long id, Model model){
        jadwalSidangSeminarService.deletesJadwalSidangSeminar(id);
        return "deleteSempro";
    }

    @GetMapping("/jadwalSidangProposal/setJadwal/{id}")
    public String SetJadwalSidangProposalFormPage(@PathVariable("id") Long id, Model model){
        JadwalSidangModel setJadwalSempro = jadwalSidangSeminarService.getJadwalSidangById(id);
        model.addAttribute("setJadwalSempro", setJadwalSempro);
        return "form-set-jadwalSempro";
    }

    @PostMapping("/jadwalSidangProposal/setJadwal/{id}")
    public String SetJadwalSidangProposalSubmitPage(@PathVariable("id") Long id,
                                                    @ModelAttribute JadwalSidangModel jadwalSempro){
        jadwalSempro.setIdJadwalSidang(id);
        jadwalSidangSeminarService.setJadwalSidang(jadwalSempro);
        return "redirect:/jadwalSidangProposal";
    }

}
