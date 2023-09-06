package com.practice.assignment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {

    private final AuthenticationService authenticationService;

    @Autowired
    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
   @RequestMapping(value="/login", method=RequestMethod.GET)
   public String loginPage() {
    System.out.println("loginpage");
       return "login";
   }

   @RequestMapping(value="/login", method=RequestMethod.POST)
    public String dashboard(@RequestParam String loginId, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {

        // Make the API call to authenticate the user and retrieve the token
        String token = authenticationService.authenticate(loginId, password);

        if (token != null) {
            // Store the token in the session
            session.setAttribute("token", token);
            return "redirect:/dashboard"; // Redirect to the dashboard page
        } else {
            // Authentication failed
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login"; // Redirect back to the login page with an error message
        }
    }
   

}
