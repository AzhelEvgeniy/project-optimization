package com.azhel.ist41.controller;

import com.azhel.ist41.dao.exception.DuplicateUserNameException;
import com.azhel.ist41.model.User;
import com.azhel.ist41.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    UserService userService;



    @RequestMapping(value = {"/","/main"}, method = RequestMethod.GET)
    public String main(ModelMap model) {
        return "index";
    }

    @RequestMapping(value = {"","/login"}, method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error){
        ModelAndView model = new ModelAndView();
        if(error != null){
            model.addObject("error", "Invalid username or password!");
        }
        model.setViewName("views/login");

        return model;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(ModelMap model){
        return "views/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(ModelMap model, @RequestParam String user_login, @RequestParam String password_login,
                              @RequestParam String confirmPassword){
        try {
            User user = new User();
            user.setUsername(user_login);
            user.setPassword(password_login);
            user.setConfirmPassword(confirmPassword);

            userService.addUser(user);

            if (!password_login.equals(confirmPassword)) {
                model.addAttribute("error", "Passwords not equals");
                return "views/registration";
            }
            return "redirect:/login";
        }
        catch (DuplicateUserNameException ex){
            model.addAttribute("error", ex.getMessage());
            return "views/registration";
        }
    }

    @RequestMapping("/logout")
    public String logoutUrl(){
        return "logout";
    }
}
