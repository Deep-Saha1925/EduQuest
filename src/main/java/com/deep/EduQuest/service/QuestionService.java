package com.deep.EduQuest.service;

import com.deep.EduQuest.model.Question;
import com.deep.EduQuest.model.Quiz;
import com.deep.EduQuest.repository.QuestionRepository;
import com.deep.EduQuest.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuizService quizService;
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    public QuestionService(QuizService quizService, QuestionRepository questionRepository, QuizRepository quizRepository) {
        this.quizService = quizService;
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }

    public void addQuestionToQuiz(Long quizId, List<Question> questions) {
        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz != null){
            for(Question q : questions){
                q.setQuiz(quiz);
                quiz.getQuestions().add(q);
            }
            quizRepository.save(quiz);
        }else{
            System.out.println("No quiz on the id");
        }
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}
