package com.deep.EduQuest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }

    @GetMapping("/quiz")
    public String quizPage() {
        return "quiz";
    }
}