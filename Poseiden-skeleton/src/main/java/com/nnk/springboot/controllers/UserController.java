package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    //@Autowired
    private UserRepository userRepository;
    private UserService userService;
    public UserController(UserService userService, UserRepository userRepository){
        this.userService=userService;
        this.userRepository=userRepository;
    }
    //la requete /user/list > renvoie une page "user/list" avec de la data (liste)
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        log.debug("inside home function, requestMapping /user/list");
        model.addAttribute("users", userRepository.findAll());
        log.debug("attribute list of users added");
        return "user/list";
    }
    //la requete GET /user/add > renvoie une page vide : "user/add"
    @GetMapping("/user/add")
    public String addUser(User bid) {
        System.out.println("ingetMapping method for /user.add"+ bid.getUsername());
        log.debug("in addUser Display method");
        return "user/add";
    }
    //la requete POST se trouve sur la page "user/add".
    //il ajoute un user et redirige vers la
    //méthode /user/list qui renvoie user/list nourrie du nouveau modèle
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        //System.out.println("hello");
        //System.out.println(user.getUsername()+" " +user.getFullname()+" "+user.getRole());
        if (!result.hasErrors()) {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);

            //System.out.println(userRepository.findUserByUsername("jdoe").getFullname());
            model.addAttribute("users", userRepository.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }
    //cette request GET se trouve sur la page user/list, elle demande l'affichage d'un formulaire de mise à jour relative
    //à un user determiné. ce formulaire est donc nourri d'un model.il retourne ce formulaire
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer userId, Model model) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }
    //cette request POST se trouve sur la page user/update. c'st une validation de formulaire
    //elle retourne la méthode/user/list qui renvoie la page user/list
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
    //c'est bizarrement une méthode get, qui se trouve dans la liste des users et renvoie la liste
    //des users avec un user en moins (donc en passant par une requete mapping)

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
