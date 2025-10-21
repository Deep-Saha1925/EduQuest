package com.deep.EduQuest.controller;

import com.deep.EduQuest.model.Question;
import com.deep.EduQuest.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final QuizService quizService;

    public AdminController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/")
    public String adminPanel(Model model) {
        model.addAttribute("questions", quizService.getAllQuestions());
        return "admin";
    }

    @GetMapping("/question/new")
    public String newQuestionForm(Model model) {
        model.addAttribute("question", new Question());
        return "question-form";
    }

    @GetMapping("/question/edit/{id}")
    public String editQuestionForm(@PathVariable Long id, Model model) {
        Question question = quizService.getQuestionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question Id:" + id));
        model.addAttribute("question", question);
        return "question-form";
    }

    @PostMapping("/question/save")
    public String saveQuestion(@Valid @ModelAttribute Question question,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "question-form";
        }
        quizService.saveQuestion(question);
        return "redirect:/admin";
    }

    @GetMapping("/question/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        quizService.deleteQuestion(id);
        return "redirect:/admin";
    }

}
