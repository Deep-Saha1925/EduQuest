package com.deep.EduQuest.controller;

import com.deep.EduQuest.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final QuizService quizService;

    public PageController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/")
    public String showDashboard(Model model) {
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "dashboard"; // Thymeleaf template
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/quiz")
    public String quizPage() {
        return "quiz";
    }
}