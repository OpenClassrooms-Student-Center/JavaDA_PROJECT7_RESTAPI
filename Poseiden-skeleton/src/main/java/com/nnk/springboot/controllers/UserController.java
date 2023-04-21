package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
//@Validated VALIDATED COMBINED WITH VALID CREATES 500 ERROR INSTEAD OF DISPLAYING ERROR ON HTML
@Controller
public class UserController {
    static final Logger log = LogManager.getLogger("com.nnk.springboot.MyAppLogger");
    //@Autowired

    private UserService userService;

    User user = new User();
    public UserController(UserService userService){
        this.userService=userService;

    }
    //la requete /user/list > renvoie une page "user/list" avec de la data (liste)
    @RequestMapping("/user/list")
    public String homeDisplayUserList(Model model) {
        log.info("REQUEST /user/list");
        model.addAttribute("users", userService.findAllUsers());
        log.debug("attribute list of users added");
        return "user/list";
    }
    //la requete GET /user/add > renvoie une page vide : "user/add"
    @GetMapping("/user/add")
    public String displayAddUserForm(User user) {
        log.debug("in addUser Display method");
        return "user/add";
    }
    //la requete POST se trouve sur la page "user/add".
    //il ajoute un user et redirige vers la
    //méthode /user/list qui renvoie user/list nourrie du nouveau modèle
    @PostMapping("/user/validate")
    public String validateUser(@Valid User user, BindingResult result, Model model) {

        log.info("POST /user/validate for username " + user.getUsername());
        /*
        }*/
        try {
            if (result.hasErrors()) {
                log.error("user to create has errors");
                throw (new Exception());
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.validateNewUser(user);
            //model.addAttribute("users", userService.findAllUsers());
            log.info("user " + user.getUsername() + "saved");
        } catch (Exception e) {
            log.error("user could not be created");
            return "user/add";
        }
    return "redirect:/user/list";
    }
    //cette request GET se trouve sur la page user/list, elle demande l'affichage d'un formulaire de mise à jour relative
    //à un user determiné. ce formulaire est donc nourri d'un model.il retourne ce formulaire
    @GetMapping("/user/update/{id}")
    public String displayUpdateUserForm(@PathVariable("id") Integer userId, Model model) {
        try{
            log.info("GET /user/update/{id} with id "+ userId);
            User user = userService.getById(userId);
            user.setPassword("");
            model.addAttribute("user", user);
            log.info("user "+ user.getId()+ " retrieved");
            return "user/update";
        }catch(Exception e){
            log.error("user update form with id "+ userId +" could not be displayed");
            return "user/list";
        }

    }
    //cette request POST se trouve sur la page user/update. c'st une validation de formulaire
    //elle retourne la méthode/user/list qui renvoie la page user/list
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        log.info("POST /user/update/{id} with id "+ id );

        try{
            if (result.hasErrors()) {
                log.error("user to update has errors");
                throw new Exception();
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));

            userService.updateUser(id, user);
            //model.addAttribute("users", userRepository.findAll());
            log.info ("user with id "+ user.getId() + " is saved" );
        }catch(Exception e){
            log.error("user with id "+id+" could not be update");
            return "redirect:/user/update/"+id+"";
        }
    return "redirect:/user/list";
    }
    //c'est bizarrement une méthode get, qui se trouve dans la liste des users et renvoie la liste
    //des users avec un user en moins (donc en passant par une requete mapping)

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        try{
            log.info("GET /user/delete/{id}");
            userService.deleteUser(id);
            log.info("user deleted");
            return "redirect:/user/list";

        }catch(Exception e){
            log.error("user with id "+id+" could not be deleted");
            return "user/list";
        }
    }
}
