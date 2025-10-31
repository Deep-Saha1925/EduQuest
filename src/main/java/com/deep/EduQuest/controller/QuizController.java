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
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/category/{category}")
    public String selectCategory(@PathVariable String category, Model model) {
        model.addAttribute("category", category);
        model.addAttribute("questionCount", quizService.getQuestionsByCategory(category).size());
        return "category-selection";
    }

    @GetMapping("/start")
    public String startQuiz(@RequestParam(defaultValue = "10") int count,
                            @RequestParam(required = false) String category,
                            @RequestParam(defaultValue = "10") int timeLimit,
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

        //set timer
        long currentTime = System.currentTimeMillis();
        long endTime = currentTime + (timeLimit * 60 * 1000);   //minutes to milliseconds
        session.setAttribute("quizEndTime", endTime);
        session.setAttribute("quizTimeLimit", timeLimit);

        if (questions.isEmpty()) {
            model.addAttribute("error", "No questions available for this category. Please try another category.");
            List<String> categories = quizService.getAllCategories();
            model.addAttribute("categories", categories);
            return "index";
        }

        return "redirect:/quiz/question";
    }

    @GetMapping("/question")
    public String showQuestion(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute("quizQuestions");
        Integer currentIndex = (Integer) session.getAttribute("currentQuestionIndex");
        Long endTime = (Long) session.getAttribute("quizEndTime");

        if (questions == null || currentIndex == null) {
            return "redirect:/";
        }

        //checking if time is up
        if(endTime != null && System.currentTimeMillis() > endTime){
            return "redirect:/quiz/timeout";
        }

        if (currentIndex >= questions.size()) {
            return "redirect:/quiz/result";
        }

        Question currentQuestion = questions.get(currentIndex);
        model.addAttribute("question", currentQuestion);
        model.addAttribute("questionNumber", currentIndex + 1);
        model.addAttribute("totalQuestions", questions.size());
        model.addAttribute("quizEndTime", endTime);

        return "question";
    }

    @PostMapping("/answer")
    public String submitAnswer(@RequestParam(required = false) String answer,
                               HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> userAnswers = (List<String>) session.getAttribute("userAnswers");
        Integer currentIndex = (Integer) session.getAttribute("currentQuestionIndex");
        Long endTime = (Long) session.getAttribute("quizEndTime");

        // Check if time is up before processing answer
        if (endTime != null && System.currentTimeMillis() > endTime) {
            return "redirect:/quiz/timeout";
        }

        if (userAnswers != null && currentIndex != null) {
            userAnswers.add(answer != null ? answer : "");
            session.setAttribute("currentQuestionIndex", currentIndex + 1);
        }

        return "redirect:/quiz/question";
    }

    @GetMapping("/timeout")
    public String quizTimeout(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute("quizQuestions");
        @SuppressWarnings("unchecked")
        List<String> userAnswers = (List<String>) session.getAttribute("userAnswers");

        // Fill remaining answers as empty
        if (questions != null && userAnswers != null) {
            while (userAnswers.size() < questions.size()) {
                userAnswers.add("");
            }
        }

        session.setAttribute("timedOut", true);
        return "redirect:/quiz/result";
    }

    @GetMapping("/result")
    public String showResult(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        List<Question> questions = (List<Question>) session.getAttribute("quizQuestions");
        @SuppressWarnings("unchecked")
        List<String> userAnswers = (List<String>) session.getAttribute("userAnswers");
        String category = (String) session.getAttribute("quizCategory");
        Integer timeLimit = (Integer) session.getAttribute("quizTimeLimit");
        Boolean timedOut = (Boolean) session.getAttribute("timedOut");

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
        model.addAttribute("timeLimit", timeLimit != null ? timeLimit : 0);
        model.addAttribute("timedOut", timedOut != null && timedOut);

        return "result";
    }

}