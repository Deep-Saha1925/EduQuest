package com.deep.EduQuest.controller;

import com.deep.EduQuest.model.Question;
import com.deep.EduQuest.model.Quiz;
import com.deep.EduQuest.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
