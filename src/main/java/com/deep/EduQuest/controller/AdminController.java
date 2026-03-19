package com.deep.EduQuest.controller;

import com.deep.EduQuest.model.Question;
import com.deep.EduQuest.service.FileUploadService;
import com.deep.EduQuest.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final QuizService quizService;
    private final FileUploadService fileUploadService;

    public AdminController(QuizService quizService, FileUploadService fileUploadService) {
        this.quizService = quizService;
        this.fileUploadService = fileUploadService;
    }

//    @GetMapping
//    public String adminPanel(Model model) {
//        model.addAttribute("questions", quizService.getAllQuestions());
//        return "admin";
//    }

    @GetMapping
    public String adminPanel(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(required = false, defaultValue = "") String category
                             ){

        category = category.trim();
        Page<Question> questionPage = quizService.findByCategoryPaginated(category, page, size);
        model.addAttribute("questions",    questionPage.getContent());
        model.addAttribute("currentPage",  questionPage.getNumber());
        model.addAttribute("totalPages",   questionPage.getTotalPages());
        model.addAttribute("totalItems",   questionPage.getTotalElements());
        model.addAttribute("pageSize",     size);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories",      quizService.getAllCategories());
        model.addAttribute("totalQuestions",  quizService.getAllQuestions().size());

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

    @PostMapping("/upload")
    public String uploadQuestions(@RequestParam("file")MultipartFile file,
                                  RedirectAttributes redirectAttributes){

        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("uploadError", "Please select a file.");
            return "redirect:/admin";
        }

        try{
            List<Question> questions = fileUploadService.parseFile(file);
            questions.forEach(quizService::saveQuestion);
            redirectAttributes.addFlashAttribute("uploadSuccess", questions.size() + " questions uploaded successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("uploadError",
                    "Upload failed: " + e.getMessage());
        }
        return "redirect:/admin";
    }

}