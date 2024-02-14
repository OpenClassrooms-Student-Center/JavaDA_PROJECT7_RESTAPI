package com.nnk.springboot.controllers;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nnk.springboot.configuration.SpringSecurityConfig;
import com.nnk.springboot.repositories.UsersRepository;
import com.nnk.springboot.service.LoggerApi;
import com.nnk.springboot.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Cette classe permet de logger un utilisateurs selon son rôle et son logout
 * 
 * @author Claudiu VILAU
 * 
 */
@Controller
@RequestMapping("app")
public class LoginController {

    ModelAndView mav = new ModelAndView();

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private SpringSecurityConfig securityConfig;

    @Autowired
    private UsersService userService;

    @Autowired
    private LoggerApi loggerApi;

    // Récupération de notre logger.
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    public LoginController() {
    }

    public LoginController(ModelAndView mav, UsersRepository userRepository, SpringSecurityConfig securityConfig,
            UsersService userService, LoggerApi loggerApi) {
        this.mav = mav;
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
        this.userService = userService;
        this.loggerApi = loggerApi;
    }

    public ModelAndView getMav() {
        return this.mav;
    }

    public void setMav(ModelAndView mav) {
        this.mav = mav;
    }

    public UsersService getUserService() {
        return this.userService;
    }

    public void setUserService(UsersService userService) {
        this.userService = userService;
    }

    public LoggerApi getLoggerApi() {
        return this.loggerApi;
    }

    public void setLoggerApi(LoggerApi loggerApi) {
        this.loggerApi = loggerApi;
    }

    public UsersRepository getUserRepository() {
        return this.userRepository;
    }

    public void setUserRepository(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SpringSecurityConfig getSecurityConfig() {
        return this.securityConfig;
    }

    public void setSecurityConfig(SpringSecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    /**
     * 
     * Cette méthode affiche la page Login d'authentification
     * 
     * @param principal est un objet qui est transmis vers l'application web client
     *                  et qui contient divers détails sur l'utilisateur
     * @param request   cet objet contient les données de la requête et des
     *                  informations sur le client une requête selon le protocole
     *                  http
     * @param response  la réponse de la servlet selon le protocole http
     * @return des valeurs à la vue
     */
    @GetMapping("login")
    public ModelAndView login(Principal principal, HttpServletRequest request, HttpServletResponse response) {

        mav.setViewName("login");

        String messageLoggerInfo = "URI active : /login Code response : "
                + HttpStatus.valueOf(response.getStatus());
        LOGGER.info(messageLoggerInfo);

        return mav;
    }

    /**
     * 
     * Cette méthode affiche la page HOME si l'utilisateur a été authentifié avec
     * succès
     * 
     * @param model    fournit des attributs utilisés pour le rendu de la vue
     * @param request  cet objet contient les données de la requête et des
     *                 informations sur le client une requête selon le protocole
     *                 http
     * @param response la réponse de la servlet selon le protocole http
     * @return des valeurs à la vue
     */
    @Secured({ "USER", "ADMIN" })
    @GetMapping("login/ok")
    public ModelAndView loginOk(Model model, HttpServletRequest request, HttpServletResponse response) {

        mav.setViewName("home");

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return mav;
    }

    /**
     * 
     * Cette méthode déconnecte l'utilisateur de l'application. Elle doit être de
     * type POST
     * 
     * @param model     fournit des attributs utilisés pour le rendu de la vue
     * @param principal est un objet qui est transmis vers l'application web client
     *                  et qui contient divers détails sur l'utilisateur *
     * @param request   cet objet contient les données de la requête et des
     *                  informations sur le client une requête selon le protocole
     *                  http
     * @param response  la réponse de la servlet selon le protocole http
     * @return des valeurs à la vue
     */
    @Secured({ "USER", "ADMIN" })
    @PostMapping("app-logout")
    public ModelAndView appLogout(Model model, Principal principal, HttpServletRequest request,
            HttpServletResponse response) {

        mav.setViewName("login");

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return mav;
    }

    /**
     * 
     * Cette méthode modifie, supprime, ajoute et affiche des utilisateurs
     * 
     * @param request  cet objet contient les données de la requête et des
     *                 informations sur le client une requête selon le protocole
     *                 http
     * @param response la réponse de la servlet selon le protocole http
     * @return des valeurs à la vue
     */
    @Secured("ADMIN")
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles(HttpServletRequest request, HttpServletResponse response) {

        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return mav;
    }

    @Secured({ "USER", "ADMIN" })
    @GetMapping("error")
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response) {

        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("/error/403");

        String messageLoggerInfo = loggerApi.loggerInfoController(request, response, "");
        LOGGER.info(messageLoggerInfo);

        return mav;
    }

}
