package protensi.sita.controller;

import protensi.sita.model.MahasiswaModel;
import protensi.sita.service.MahasiswaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MahasiswaController {
    @Autowired
    private MahasiswaServiceImpl mahasiswaService;

    @GetMapping("/mahasiswa/viewall")
    public String viewAllMahasiswa(Model model) {
        List<MahasiswaModel> listMahasiswa = mahasiswaService.findAllMahasiswa();
        model.addAttribute("listMahasiswa", listMahasiswa);
        return "user/mahasiswa-viewall";
    }

    @GetMapping("/mahasiswa/add")
    public String addMahasiswaFormPage(Model model) {
        MahasiswaModel mahasiswa = new MahasiswaModel();
        //mahasiswa.setRole(EnumRole.MAHASISWA); Aldin nanti ini di benerin ya set role nya yang sesuai karena roles nya kan Set<EnumRole>
        model.addAttribute("mahasiswa", mahasiswa);
        return "user/mahasiswa-add-form";
    }

    @PostMapping(value = "/mahasiswa/add", params = { "save" })
    public String addMahasiswaSubmitPage(@ModelAttribute MahasiswaModel mahasiswa, Model model) {
        mahasiswaService.addMahasiswa(mahasiswa);
        model.addAttribute("nama", mahasiswa.getNama());
        return "user/mahasiswa-add-submited";
    }
}
