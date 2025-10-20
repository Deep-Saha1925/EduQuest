package com.deep.EduQuest.service;

import com.deep.EduQuest.model.Question;
import com.deep.EduQuest.model.Quiz;
import com.deep.EduQuest.repository.QuestionRepository;
import com.deep.EduQuest.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    //fetch quiz by ID
    public Quiz getQuizById(Long id){
        return quizRepository.findById(id).orElse(null);
    }

    //fetch only question ids for a quiz
    public List<Long> getQuestionIdsByQuiz(long quizId){
        List<Question> questions = questionRepository.findByQuizId(quizId);
        Collections.shuffle(questions);
        return questions.stream().map(Question::getId).toList();
    }

    // Get question by QUIZ_ID
    public List<Question> getQuestionsById(Long quizId){
        return questionRepository.findByQuizId(quizId);
    }

    //creating a new quiz
    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }
}
