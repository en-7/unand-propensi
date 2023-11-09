package protensi.sita.controller;

import protensi.sita.model.MahasiswaModel;
import protensi.sita.model.EnumRole;
import protensi.sita.model.UserModel;
import protensi.sita.service.MahasiswaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<EnumRole> roleMahasiswa = new HashSet<EnumRole>();
        roleMahasiswa.add(EnumRole.ADMIN); // dah ya cici
        mahasiswa.setRoles(roleMahasiswa);
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
