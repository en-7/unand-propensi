package protensi.sita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import protensi.sita.model.MahasiswaModel;
import protensi.sita.service.MahasiswaServiceImpl;

@Controller
public class BaseController {

    @Autowired
    private MahasiswaServiceImpl mahasiswaService;

    @GetMapping("/")
    private String home(){
        MahasiswaModel m = new MahasiswaModel();
        m = mahasiswaService.findMahasiswaById(3);
        System.out.println("###nama mahasiswa: "+ m.getNama());
        return "home";
    }
    /*@GetMapping("/error")
    private String Error() {
        return "error";
    }*/    
}
