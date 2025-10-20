package com.deep.EduQuest.controller;

import com.deep.EduQuest.model.Result;
import com.deep.EduQuest.service.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService resultService;
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    //save result
    @PostMapping
    public ResponseEntity<Result> saveResult(@RequestBody Result result){
        return ResponseEntity.ok(resultService.saveResult(result));
    }

    //get all results of a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Result>> getResultsByUser(@PathVariable Long userId){
        return ResponseEntity.ok(resultService.getResultByUserId(userId));
    }

    //get all results
    @GetMapping
    public ResponseEntity<List<Result>> getAllResults(){
        return ResponseEntity.ok(resultService.getAllResults());
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitAnswers(@RequestBody Result result){
        resultService.saveResult(result);
        return ResponseEntity.ok("Result Submitted Successfully.");
    }
}
