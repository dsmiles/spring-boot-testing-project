package com.github.dsmiles.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class responsible for handling requests related to the dashboard.
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    /**
     * Handles GET requests for the dashboard endpoint.
     *
     * @param model the model to be populated with attributes for the view
     * @return the name of the view to render
     */
    @GetMapping
    public String getDashboard(Model model) {
        model.addAttribute("message", "Hello World!");
        return "dashboard";
    }
}
