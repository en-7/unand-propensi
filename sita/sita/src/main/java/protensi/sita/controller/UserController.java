// package protensi.sita.controller;

// import protensi.sita.model.EnumRole;
// import protensi.sita.model.UserModel;
// import protensi.sita.repository.UserDb;
// import protensi.sita.service.EnumRoleService;
// import protensi.sita.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// import java.util.Set;

// @Controller
// @RequestMapping("/user")
// public class UserController {
// @Autowired
// private UserService userService;

// @Autowired
// private EnumRoleService enumRoleService;

// @GetMapping(value = "/add")
// private String addUserFormPage(Model model) {
// UserModel user = new UserModel();
// Set<EnumRole> listRole = enumRoleService.findAll();
// model.addAttribute("user", user);
// model.addAttribute("listRole", listRole);
// return "form-add-user";
// }
// }
