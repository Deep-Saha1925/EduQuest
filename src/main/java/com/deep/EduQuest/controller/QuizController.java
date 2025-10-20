package com.deep.EduQuest.controller;

import com.deep.EduQuest.model.Question;
import com.deep.EduQuest.model.Quiz;
import com.deep.EduQuest.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    //create a quiz
    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz){
        return ResponseEntity.ok(quizService.createQuiz(quiz));
    }

    //get all quizzes
    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes(){
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    //get quiz by id
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id){
        Quiz quiz = quizService.getQuizById(id);
        return quiz != null ? ResponseEntity.ok(quiz) : ResponseEntity.notFound().build();
    }

    //get all questions for a quiz
    @GetMapping("/{id}/questions")
    public ResponseEntity<List<Question>> getQuestionsByQuizId(@PathVariable Long id){
        Quiz quiz = quizService.getQuizById(id);
        return quiz != null ? ResponseEntity.ok(quizService.getQuestionsById(id)) : ResponseEntity.notFound().build();
    }

    //get Random question ids for a quiz
    @GetMapping("/{quizId}/random-questions")
    public ResponseEntity<List<Long>> getRandomQuestionsByQuizId(@PathVariable Long quizId){
        Quiz quiz = quizService.getQuizById(quizId);
        return quiz != null ? ResponseEntity.ok(quizService.getQuestionIdsByQuiz(quizId)) : ResponseEntity.notFound().build();
    }

    //Delete quiz
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Long id){
        quizService.deleteQuiz(id);
        return ResponseEntity.ok("Quiz deleted Successfully.");
    }



    @GetMapping("/quiz/{id}")
    public String showQuizPage(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.getQuizById(id);
        model.addAttribute("quiz", quiz);
        return "quiz";
    }

    //get batch of questions
    @GetMapping("/{quizId}/questions/batch")
    public ResponseEntity<List<Question>> getQuestionsBatch(
            @PathVariable Long quizId,
            @RequestParam(defaultValue = "0") int batch
    ){
        int batchSize = 5;
        List<Question> questions = quizService.getQuestionsByBatch(quizId, batch, batchSize);
        return ResponseEntity.ok(questions);
    }
}
