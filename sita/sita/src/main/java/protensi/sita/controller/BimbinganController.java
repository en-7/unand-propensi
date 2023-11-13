package protensi.sita.controller;

import protensi.sita.model.AvailableBimbinganModel;
import protensi.sita.model.EnumRole;
import protensi.sita.model.JadwalBimbinganModel;
import protensi.sita.model.PembimbingModel;
import protensi.sita.model.SeminarProposalModel;
import protensi.sita.model.UgbModel;
import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.UserModel;
import protensi.sita.security.UserDetailsServiceImpl;
import protensi.sita.service.AvailableBimbinganServiceImpl;
import protensi.sita.service.BaseService;
import protensi.sita.service.JadwalBimbinganServiceImpl;
import protensi.sita.service.MahasiswaServiceImpl;
import protensi.sita.service.PembimbingServiceImpl;
import protensi.sita.service.UgbServiceImpl;

import java.util.ArrayList;
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

    @Qualifier("ugbServiceImpl")
    @Autowired
    private UgbServiceImpl ugbService;

    @Autowired
    public BaseService baseService;

    @GetMapping("/atur-jadwal/add")
    public String addAvailableBimbinganFormPage(Model model) {
        AvailableBimbinganModel availableBimbingan = new AvailableBimbinganModel();
        model.addAttribute("roleUser", baseService.getCurrentRole());
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
        model.addAttribute("roleUser", baseService.getCurrentRole());
        model.addAttribute("listAvailable", listAvailable);
        return "redirect:/bimbingan/atur-jadwal/";
    }

    @GetMapping("/atur-jadwal/update/{idAvailableBimbingan}")
    public String updateAvailableBimbinganFormPage(@PathVariable Long idAvailableBimbingan, Model model) {
        AvailableBimbinganModel availableBimbingan = availableBimbinganService.findById(idAvailableBimbingan);
        PembimbingModel pembimbing = availableBimbingan.getPembimbing();
        model.addAttribute("availableBimbingan", availableBimbingan);
        model.addAttribute("pembimbingId", pembimbing.getIdUser());
        model.addAttribute("roleUser", baseService.getCurrentRole());
        return "bimbingan/update-available-bimbingan-form";
    }


    @PostMapping("/atur-jadwal/update")
    public String updateAvailableBimbinganSubmitPage(@ModelAttribute AvailableBimbinganModel availableBimbingan, Model model) {
        availableBimbinganService.save(availableBimbingan);
        List<AvailableBimbinganModel> listAvailable = availableBimbinganService.findAll();
        model.addAttribute("listAvailable", listAvailable);
        model.addAttribute("roleUser", baseService.getCurrentRole());
        return "redirect:/bimbingan/atur-jadwal/";

    }

    @GetMapping("/viewall")
    public String listBimbingan(Model model, Authentication authentication) {
        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);
        if (user.getRoles().contains(EnumRole.PEMBIMBING)) {
            PembimbingModel pembimbing = pembimbingService.findPembimbingById(user.getIdUser());
            List<JadwalBimbinganModel> listBimbingan = jadwalBimbinganService.findBimbinganByIdPembimbing(pembimbing.getIdUser());
            model.addAttribute("listBimbingan", listBimbingan);
            model.addAttribute("roleUser", baseService.getCurrentRole());
            return "bimbingan/viewall-jadwal-bimbingan";

        } else if (user.getRoles().contains(EnumRole.MAHASISWA)){
            MahasiswaModel mahasiswa = mahasiswaService.findMahasiswaById(user.getIdUser());
            List<JadwalBimbinganModel> listBimbingan = jadwalBimbinganService.findBimbinganByIdMahasiswa(mahasiswa.getIdUser());
            model.addAttribute("listBimbingan", listBimbingan);
            model.addAttribute("roleUser", baseService.getCurrentRole());
            return "bimbingan/viewall-jadwal-bimbingan";

        } else{
            model.addAttribute("roleUser", baseService.getCurrentRole());
            return "bimbingan/error-bimbingan";
        }
    }

    @GetMapping("/atur-jadwal")
    public String listAvailable(Model model, Authentication authentication) {
        String namaUser = authentication.getName();
        UserModel user = userDetailsService.findByUsername(namaUser);

        if (user.getRoles().contains(EnumRole.PEMBIMBING)) {
            PembimbingModel pembimbing = pembimbingService.findPembimbingById(user.getIdUser());
            List<AvailableBimbinganModel> listAvailable = availableBimbinganService.findAllByIdPembimbing(pembimbing.getIdUser());
            if (listAvailable.isEmpty()){
                model.addAttribute("roleUser", baseService.getCurrentRole());
                return "bimbingan/error-bimbingan";
            }else {
                model.addAttribute("listAvailable", listAvailable);
                model.addAttribute("roleUser", baseService.getCurrentRole());
                return "bimbingan/viewall-available-bimbingan";
            }
           

        } else if (user.getRoles().contains(EnumRole.MAHASISWA)){
            MahasiswaModel mahasiswa = mahasiswaService.findMahasiswaById(user.getIdUser());
            UgbModel ugb = ugbService.findByIdMahasiswa(mahasiswa);
            if (ugb == null){
                model.addAttribute("roleUser", baseService.getCurrentRole());
                return "bimbingan/error-bimbingan";
            }else {
                List<AvailableBimbinganModel> listAvailable = availableBimbinganService.listAvailablePembimbing(ugb);
                List<JadwalBimbinganModel> listBimbingan = jadwalBimbinganService.findBimbinganByListAvailable(listAvailable);
                model.addAttribute("user", mahasiswa);
                model.addAttribute("listBimbingan", listBimbingan);
                model.addAttribute("listAvailable", listAvailable);
                model.addAttribute("roleUser", baseService.getCurrentRole());
                return "bimbingan/viewall-booking-bimbingan";
            }

        } else{
            model.addAttribute("roleUser", baseService.getCurrentRole());
            return "bimbingan/error-bimbingan";
        }
    }

    @GetMapping("/atur-jadwal/delete/{idAvailableBimbingan}")
    public String deleteAvailableBimbingan(@PathVariable Long idAvailableBimbingan, Model model) {
        AvailableBimbinganModel availableBimbingan = availableBimbinganService.findById(idAvailableBimbingan);
        availableBimbinganService.delete(idAvailableBimbingan);
        model.addAttribute("roleUser", baseService.getCurrentRole());
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
        model.addAttribute("roleUser", baseService.getCurrentRole());
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
        model.addAttribute("roleUser", baseService.getCurrentRole());
        model.addAttribute("availableBimbingan", availableBimbingan);
        return "redirect:/bimbingan/atur-jadwal/";
    }
    
}
