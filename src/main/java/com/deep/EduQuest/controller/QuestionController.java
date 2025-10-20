package com.deep.EduQuest.controller;

import com.deep.EduQuest.model.Question;
import com.deep.EduQuest.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    //add questions to a quiz
    @PostMapping("/{quizId}")
    public ResponseEntity<String> addQuestion(@PathVariable Long quizId, @RequestBody List<Question> questions){
        questionService.addQuestionToQuiz(quizId, questions);
        return ResponseEntity.ok("Questions add to quizId: " + quizId);
    }

    //Get question by id
    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id){
        Question question = questionService.getQuestionById(id);
        return question != null ? ResponseEntity.ok(question) : ResponseEntity.notFound().build();
    }

    //Get all questions
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions(){
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

}
