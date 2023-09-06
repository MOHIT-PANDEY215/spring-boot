package com.practice.assignment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
    private final CustomerService customerService; 

    @Autowired
    public DashboardController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public String getTokenFromSession(HttpSession session) {
        return (String) session.getAttribute("token");
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        // Return the view name for your dashboard page
        return "dashboard"; 
    }

    // Handle the addition of a new customer
    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Customer customer, HttpSession session) {
        // Retrieve the token from the session
        String token = (String) session.getAttribute("token");

        // Make an authenticated API request to add the customer
        boolean success = customerService.addCustomer(customer, token);

        if (success) {
            // Redirect to the dashboard or customer list page after successfully adding the customer
            return "redirect:/dashboard";
        } else {
            // Handle the case where adding the customer failed, e.g., show an error message
            // You can add a flash attribute to display an error message on the dashboard page
            return "redirect:/dashboard?error=1";
        }
    }
}
