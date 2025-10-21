package com.deep.EduQuest.controller;

import com.deep.EduQuest.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/")
    public String home(Model model) {
        // Get all unique categories
        List<String> categories = quizService.getAllCategories();
        model.addAttribute("categories", categories);
        return "index";
    }
}
