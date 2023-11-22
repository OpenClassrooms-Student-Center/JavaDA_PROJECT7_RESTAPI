package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;

public abstract class TestVariables {
    protected Model model;
    protected Principal principal;

    protected ModelAndView mav;

    protected Rating rating;
    protected List<Rating> ratingList;
    protected Optional<Rating> ratingOptional;

    protected String longString;

    protected String ratingFitchRatingSize;
    protected String ratingMoodysRatingSize;
    protected String ratingOrderMin;
    protected String ratingOrderMax;
    protected String ratingSandPRatingSize;

    protected Integer ratingId; // id of the rating created for integration tests

    protected User user;
    protected List<User> userList;
    protected Optional<User> userOptional;
    protected String passwordIncorrect;

    protected String userUsernameSize;
    protected String userPasswordSize;
    protected String userFullNameSize;
    protected String userRoleSize;
    protected String userUsernameNotBlank;
    protected String userPasswordNotBlank;
    protected String userFullNameNotBlank;
    protected String userRoleNotBlank;
    protected Integer userId; // id of the rating created for integration tests

    public void initializeVariables() {
        model = new Model() {
            @Override
            public Model addAttribute(String attributeName, Object attributeValue) {
                return null;
            }

            @Override
            public Model addAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> attributeValues) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public boolean containsAttribute(String attributeName) {
                return false;
            }

            @Override
            public Object getAttribute(String attributeName) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        };

        principal = new Principal() {
            @Override
            public String getName() {
                return null;
            }
        };

        mav = new ModelAndView();

        rating = new Rating("moodysRatingTestValue", "sandPRatingTestValue", "UsernameTestValue", 10);
        ratingList = new ArrayList<>(List.of(rating));
        ratingOptional = Optional.of(rating);

        // 126 characters string used for size validation
        longString = "85368220096847824275049423209683" +
                "35786401076532718070358521773728" +
                "25842050212176069074456918678132" +
                "635754452771884752599433457836";

        ratingFitchRatingSize = "fitchRating should be less than 126 characters";
        ratingMoodysRatingSize = "moodysRating should be less than 126 characters";
        ratingOrderMin = "order should be more than -129";
        ratingOrderMax = "order should be less than 128";
        ratingSandPRatingSize = "sandPRating should be less than 126 characters";

        user = new User();
        user.setUsername("usernameTestValue");
        user.setPassword("passwordTestValue1!");
        user.setFullname("fullNameTestValue");
        user.setRole("USER");
        userList = new ArrayList<>(List.of(user));
        userOptional = Optional.of(user);

        passwordIncorrect = "psswrd";

        userUsernameSize = "Username should be less than 126 characters";
        userPasswordSize = "Password should be less than 126 characters";
        userFullNameSize = "FullName should be less than 126 characters";
        userRoleSize = "Role should be less than 126 characters";
        userUsernameNotBlank = "Username is mandatory";
        userPasswordNotBlank = "Password is mandatory";
        userFullNameNotBlank = "FullName is mandatory";
        userRoleNotBlank = "Role is mandatory";
    }

}
