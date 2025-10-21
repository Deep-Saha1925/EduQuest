package com.deep.EduQuest.controller;

import com.deep.EduQuest.model.Question;
import com.deep.EduQuest.service.QuizService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/")
    public String home(Model model) {
        // Get all unique categories
        List<String> categories = quizService.getAllCategories();
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/quiz/category/{category}")
    public String selectCategory(@PathVariable String category, Model model) {
        model.addAttribute("category", category);
        model.addAttribute("questionCount", quizService.getQuestionsByCategory(category).size());
        return "category-selection";
    }

    @GetMapping("/quiz/start")
    public String startQuiz(@RequestParam(defaultValue = "10") int count,
                            @RequestParam(required = false) String category,
                            HttpSession session, Model model) {
        List<Question> questions;

        if (category != null && !category.isEmpty()) {
            questions = quizService.getRandomQuestionsByCategory(category, count);
            session.setAttribute("quizCategory", category);
        } else {
            questions = quizService.getRandomQuestions(count);
            session.setAttribute("quizCategory", "All Categories");
        }

        session.setAttribute("quizQuestions", questions);
        session.setAttribute("currentQuestionIndex", 0);
        session.setAttribute("userAnswers", new ArrayList<String>());

        if (questions.isEmpty()) {
            model.addAttribute("error", "No questions available for this category. Please try another category.");
            return "index";
        }

        return "redirect:/quiz/question";
    }

    @GetMapping("/quiz/question")
    public String showQuestion(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute("quizQuestions");
        Integer currentIndex = (Integer) session.getAttribute("currentQuestionIndex");

        if (questions == null || currentIndex == null) {
            return "redirect:/";
        }

        if (currentIndex >= questions.size()) {
            return "redirect:/quiz/result";
        }

        Question currentQuestion = questions.get(currentIndex);
        model.addAttribute("question", currentQuestion);
        model.addAttribute("questionNumber", currentIndex + 1);
        model.addAttribute("totalQuestions", questions.size());

        return "question";
    }

    @PostMapping("/quiz/answer")
    public String submitAnswer(@RequestParam(required = false) String answer,
                               HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> userAnswers = (List<String>) session.getAttribute("userAnswers");
        Integer currentIndex = (Integer) session.getAttribute("currentQuestionIndex");

        if (userAnswers != null && currentIndex != null) {
            userAnswers.add(answer != null ? answer : "");
            session.setAttribute("currentQuestionIndex", currentIndex + 1);
        }

        return "redirect:/quiz/question";
    }

    @GetMapping("/quiz/result")
    public String showResult(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute("quizQuestions");
        @SuppressWarnings("unchecked")
        List<String> userAnswers = (List<String>) session.getAttribute("userAnswers");
        String category = (String) session.getAttribute("quizCategory");

        if (questions == null || userAnswers == null) {
            return "redirect:/";
        }

        int score = quizService.calculateScore(questions, userAnswers);
        int total = questions.size();
        double percentage = (score * 100.0) / total;

        model.addAttribute("score", score);
        model.addAttribute("total", total);
        model.addAttribute("percentage", String.format("%.2f", percentage));
        model.addAttribute("questions", questions);
        model.addAttribute("userAnswers", userAnswers);
        model.addAttribute("category", category != null ? category : "General");

        return "result";
    }

    // Admin Routes
    @GetMapping("/admin")
    public String adminPanel(Model model) {
        model.addAttribute("questions", quizService.getAllQuestions());
        return "admin";
    }

    @GetMapping("/admin/question/new")
    public String newQuestionForm(Model model) {
        model.addAttribute("question", new Question());
        return "question-form";
    }

    @GetMapping("/admin/question/edit/{id}")
    public String editQuestionForm(@PathVariable Long id, Model model) {
        Question question = quizService.getQuestionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question Id:" + id));
        model.addAttribute("question", question);
        return "question-form";
    }

    @PostMapping("/admin/question/save")
    public String saveQuestion(@Valid @ModelAttribute Question question,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "question-form";
        }
        quizService.saveQuestion(question);
        return "redirect:/admin";
    }

    @GetMapping("/admin/question/delete/{id}")
    public String deleteQuestion(@PathVariable Long id) {
        quizService.deleteQuestion(id);
        return "redirect:/admin";
    }
}