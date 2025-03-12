package com.App.Lfarma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard_admin")
    public String DashboardAdmin() {
        return "dashboard_admin"; // Asegúrate de que "dashboard.html" existe
    }
}
