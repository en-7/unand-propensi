package protensi.sita.controller;

import protensi.sita.model.AvailableBimbinganModel;
import protensi.sita.model.JadwalBimbinganModel;
import protensi.sita.model.PembimbingModel;
import protensi.sita.service.AvailableBimbinganServiceImpl;
import protensi.sita.service.JadwalBimbinganServiceImpl;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/bimbingan")
public class BimbinganController {

    @Qualifier("availableBimbinganServiceImpl")
    @Autowired
    private AvailableBimbinganServiceImpl availableBimbinganService;

    @Qualifier("jadwalBimbinganServiceImpl")
    @Autowired
    private JadwalBimbinganServiceImpl jadwalBimbinganService;


    @GetMapping("/atur-jadwal/add")
    public String addAvailableBimbinganFormPage(Model model) {
        AvailableBimbinganModel availableBimbingan = new AvailableBimbinganModel();
        model.addAttribute("availableBimbingan", availableBimbingan);
        return "add-available-bimbingan-form";
    }

    @PostMapping("/atur-jadwal/add")
    public String addAvailbaleBimbinganSubmitPage(@ModelAttribute AvailableBimbinganModel availableBimbingan, Model model) {
        availableBimbinganService.add(availableBimbingan);
        model.addAttribute("availableBimbingan", availableBimbingan);
        return "viewall-available-bimbingan";
    }

    @GetMapping("/atur-jadwal/update/{idAvailableBimbingan}")
    public String updateAvailableBimbinganFormPage(@PathVariable Long idAvailableBimbingan, Model model) {
        AvailableBimbinganModel availableBimbingan = availableBimbinganService.findById(idAvailableBimbingan);
        PembimbingModel pembimbing = availableBimbingan.getPembimbing();
        model.addAttribute("availableBimbingan", availableBimbingan);
        model.addAttribute("pembimbingId", pembimbing.getIdUser());
        return "update-available-bimbingan-form";
    }


    @PostMapping("/atur-jadwal/update")
    public String updateAvailableBimbinganSubmitPage(@ModelAttribute AvailableBimbinganModel availableBimbingan, Model model) {
        availableBimbinganService.save(availableBimbingan);
        List<AvailableBimbinganModel> listAvailable = availableBimbinganService.findAll();
        model.addAttribute("listAvailable", listAvailable);
        return "redirect:/bimbingan/atur-jadwal/";

    }

    @GetMapping("/viewall")
    public String listBimbingan(Model model) {
        List<JadwalBimbinganModel> listBimbingan = jadwalBimbinganService.findAll();
        model.addAttribute("listBimbingan", listBimbingan);
        return "viewall-jadwal-bimbingan";
    }

    @GetMapping("/atur-jadwal")
    public String listAvailable(Model model) {
        List<AvailableBimbinganModel> listAvailable = availableBimbinganService.findAll();
        model.addAttribute("listAvailable", listAvailable);
        return "viewall-available-bimbingan";
    }

    @GetMapping("/atur-jadwal/delete/{idAvailableBimbingan}")
    public String deleteAvailableBimbingan(@PathVariable Long idAvailableBimbingan, Model model) {
        AvailableBimbinganModel availableBimbingan = availableBimbinganService.findById(idAvailableBimbingan);
        availableBimbinganService.delete(idAvailableBimbingan);
        model.addAttribute("availableBimbingan", availableBimbingan);
        return "redirect:/bimbingan/atur-jadwal/";
    }

    @PostMapping("/atur-jadwal/booking/{idAvailableBimbingan}")
    public String bookingJadwalBimbingan(@PathVariable Long idAvailableBimbingan, Model model) {
        JadwalBimbinganModel jadwalBimbingan = new JadwalBimbinganModel();
        AvailableBimbinganModel availableBimbingan = availableBimbinganService.findById(idAvailableBimbingan);
        jadwalBimbingan.setAvailableBimbingan(availableBimbingan);
        jadwalBimbinganService.save(jadwalBimbingan);
        jadwalBimbinganService.delete(jadwalBimbingan.getIdJadwalBimbingan());

        model.addAttribute("availableBimbingan", availableBimbingan);
        return "redirect:/bimbingan/atur-jadwal/";
    }

    @GetMapping("/atur-jadwal/cancel/{idAvailableBimbingan}")
    public String cancelJadwalBimbingan(@PathVariable Long idAvailableBimbingan, Model model) {
        JadwalBimbinganModel jadwalBimbingan = jadwalBimbinganService.findByAvaialableBimbingan(idAvailableBimbingan);
        jadwalBimbinganService.delete(jadwalBimbingan.getIdJadwalBimbingan());

        AvailableBimbinganModel availableBimbingan = availableBimbinganService.findById(idAvailableBimbingan);
        model.addAttribute("availableBimbingan", availableBimbingan);
        return "redirect:/bimbingan/atur-jadwal/";
    }
    
}
