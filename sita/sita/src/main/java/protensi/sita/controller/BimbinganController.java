package protensi.sita.controller;

import protensi.sita.model.AvailableBimbinganModel;
import protensi.sita.model.EnumRole;
import protensi.sita.model.JadwalBimbinganModel;
import protensi.sita.model.PembimbingModel;
import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.UserModel;
import protensi.sita.security.UserDetailsServiceImpl;
import protensi.sita.service.AvailableBimbinganServiceImpl;
import protensi.sita.service.JadwalBimbinganServiceImpl;
import protensi.sita.service.MahasiswaServiceImpl;
import protensi.sita.service.PembimbingServiceImpl;

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
import org.springframework.security.core.Authentication;


@Controller
@RequestMapping("/bimbingan")
public class BimbinganController {

    @Qualifier("availableBimbinganServiceImpl")
    @Autowired
    private AvailableBimbinganServiceImpl availableBimbinganService;

    @Qualifier("jadwalBimbinganServiceImpl")
    @Autowired
    private JadwalBimbinganServiceImpl jadwalBimbinganService;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Qualifier("mahasiswaServiceImpl")
    @Autowired
    private MahasiswaServiceImpl mahasiswaService;

    @Qualifier("pembimbingServiceImpl")
    @Autowired
    private PembimbingServiceImpl pembimbingService;

    @GetMapping("/atur-jadwal/add")
    public String addAvailableBimbinganFormPage(Model model) {
        AvailableBimbinganModel availableBimbingan = new AvailableBimbinganModel();
        model.addAttribute("availableBimbingan", availableBimbingan);
        return "bimbingan/add-available-bimbingan-form";
    }

    @PostMapping("/atur-jadwal/add")
    public String addAvailbaleBimbinganSubmitPage(
        @ModelAttribute AvailableBimbinganModel availableBimbingan, Model model,
        Authentication authentication) {
        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);
        PembimbingModel pembimbing = pembimbingService.findPembimbingById(user.getIdUser());
        availableBimbingan.setPembimbing(pembimbing);
        availableBimbingan.setBookingStatus("AVAILABLE");
        availableBimbinganService.add(availableBimbingan);

        List<AvailableBimbinganModel> listAvailable = availableBimbinganService.findAll();
        model.addAttribute("listAvailable", listAvailable);
        return "redirect:/bimbingan/atur-jadwal/";
    }

    @GetMapping("/atur-jadwal/update/{idAvailableBimbingan}")
    public String updateAvailableBimbinganFormPage(@PathVariable Long idAvailableBimbingan, Model model) {
        AvailableBimbinganModel availableBimbingan = availableBimbinganService.findById(idAvailableBimbingan);
        PembimbingModel pembimbing = availableBimbingan.getPembimbing();
        model.addAttribute("availableBimbingan", availableBimbingan);
        model.addAttribute("pembimbingId", pembimbing.getIdUser());
        return "bimbingan/update-available-bimbingan-form";
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
        return "bimbingan/viewall-jadwal-bimbingan";
    }

    @GetMapping("/atur-jadwal")
    public String listAvailable(Model model, Authentication authentication) {
        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);
        PembimbingModel pembimbing = pembimbingService.findPembimbingById(user.getIdUser());
        if (user.getRoles().contains(EnumRole.PEMBIMBING)) {
            List<AvailableBimbinganModel> listAvailable = availableBimbinganService.findAllByIdPembimbing(pembimbing.getIdUser());
            model.addAttribute("listAvailable", listAvailable);
            return "bimbingan/viewall-available-bimbingan";
        } else if (user.getRoles().contains(EnumRole.MAHASISWA)){
            List<AvailableBimbinganModel> listAvailable = availableBimbinganService.findAll();
            model.addAttribute("listAvailable", listAvailable);
            return "bimbingan/viewall-booking-bimbingan";
        } else{
            return "bimbingan/error-bimbingan";
        }
    }

    @GetMapping("/atur-jadwal/delete/{idAvailableBimbingan}")
    public String deleteAvailableBimbingan(@PathVariable Long idAvailableBimbingan, Model model) {
        AvailableBimbinganModel availableBimbingan = availableBimbinganService.findById(idAvailableBimbingan);
        availableBimbinganService.delete(idAvailableBimbingan);
        model.addAttribute("availableBimbingan", availableBimbingan);
        return "redirect:/bimbingan/atur-jadwal/";
    }

    @PostMapping("/atur-jadwal/booking/{idAvailableBimbingan}")
    public String bookingJadwalBimbingan(@PathVariable Long idAvailableBimbingan, Model model, Authentication authentication) {
        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);
        MahasiswaModel mahasiswa = mahasiswaService.findMahasiswaById(user.getIdUser());

        JadwalBimbinganModel jadwalBimbingan = new JadwalBimbinganModel();
        AvailableBimbinganModel availableBimbingan = availableBimbinganService.findById(idAvailableBimbingan);
        jadwalBimbingan.setAvailableBimbingan(availableBimbingan);
        jadwalBimbingan.setMahasiswa(mahasiswa);
        jadwalBimbinganService.save(jadwalBimbingan);

        availableBimbingan.setBookingStatus("BOOKED");
        availableBimbinganService.save(availableBimbingan);
        model.addAttribute("availableBimbingan", availableBimbingan);
        return "redirect:/bimbingan/atur-jadwal/";
    }

    @GetMapping("/atur-jadwal/cancel/{idAvailableBimbingan}")
    public String cancelJadwalBimbingan(@PathVariable Long idAvailableBimbingan, Model model) {
        JadwalBimbinganModel jadwalBimbingan = jadwalBimbinganService.findByAvaialableBimbingan(idAvailableBimbingan);
        jadwalBimbinganService.delete(jadwalBimbingan.getIdJadwalBimbingan());

        AvailableBimbinganModel availableBimbingan = availableBimbinganService.findById(idAvailableBimbingan);
        availableBimbingan.setBookingStatus("AVAILABLE");
        availableBimbinganService.save(availableBimbingan);
        model.addAttribute("availableBimbingan", availableBimbingan);
        return "redirect:/bimbingan/atur-jadwal/";
    }
    
}
