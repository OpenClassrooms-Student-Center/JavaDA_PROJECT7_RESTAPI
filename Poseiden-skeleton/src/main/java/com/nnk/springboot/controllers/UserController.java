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
    static final Logger log = LogManager.getLogger();
    //@Autowired
    private UserRepository userRepository;
    private UserService userService;
    public UserController(UserService userService, UserRepository userRepository){
        this.userService=userService;
        this.userRepository=userRepository;
    }

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
        System.out.println("ingetMapping method for /user.add"+ bid.getUsername());
        log.debug("in addUser Display method");
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        System.out.println("hello");
        System.out.println(user.getUsername()+" " +user.getFullname()+" "+user.getRole());
        if (!result.hasErrors()) {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);

            System.out.println(userRepository.findUserByUsername("jdoe").getFullname());
            model.addAttribute("users", userRepository.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }
    /*@PostMapping("/user/validate")
    public String registerNewUser(@RequestParam(name = "username")String username, @RequestParam(name = "password")String password, @RequestParam(name="fullname")String fullname, @RequestParam(name="role")String role){
        log.warn("in register method");
        System.out.println("in register method");
        User newUser= userService.registerNewUser(username, password, fullname, role);
        log.warn("out register method, back to controller");
        System.out.println("out register method, back in controller");
        return "redirect:/home";
    }*/
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer userId, Model model) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

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

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
