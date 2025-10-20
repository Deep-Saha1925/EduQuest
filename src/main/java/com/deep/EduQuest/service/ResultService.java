package com.deep.EduQuest.service;

import com.deep.EduQuest.model.Result;
import com.deep.EduQuest.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;

    public Result saveResult(Result result){
        return resultRepository.save(result);
    }

    public List<Result> getResultByUserId(Long userId) {
        return resultRepository.findByUserId(userId);
    }

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }
}
