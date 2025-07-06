package com.soft.gymapp.presentation.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("titulo", "GymApp");
        model.addAttribute("slogan", "Caratula inicial para app de gestión de gimnasios, esto desde el controlador");
        model.addAttribute("fecha", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return "home";
    }
}