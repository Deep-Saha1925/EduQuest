package com.deep.EduQuest.service;

import com.deep.EduQuest.model.Question;
import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileUploadService {

    public List<Question> parseFile(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename().toLowerCase();
        if(filename.endsWith(".csv")){
            return parseCSV(file);
        } else if(filename.endsWith(".xlsx") || filename.endsWith(".xls")){
            return parseExcel(file);
        } else {
            throw new IllegalArgumentException("Unsupported file type. Use .csv or .xlsx");
        }
    }

    private List<Question> parseCSV(MultipartFile file) throws Exception{
        List<Question> questions = new ArrayList<>();
        try(CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))){
            String[] line;
            boolean firstRow = true;
            while((line = reader.readNext()) != null){
                if(firstRow) {
                    firstRow = false;
                    continue;
                }
                if(line.length < 6) continue;
                questions.add(buildQuestion(line));
            }
        }
        return questions;
    }

    private List<Question> parseExcel(MultipartFile file) throws Exception {
        List<Question> questions = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            boolean firstRow = true;
            for (Row row : sheet) {
                if (firstRow) { firstRow = false; continue; } // skip header
                if (row.getCell(0) == null) continue;
                String[] line = new String[8];
                for (int i = 0; i < 8; i++) {
                    Cell cell = row.getCell(i);
                    line[i] = (cell != null) ? cell.toString().trim() : "";
                }
                questions.add(buildQuestion(line));
            }
        }
        return questions;
    }

    private Question buildQuestion(String[] cols) {
        Question q = new Question();
        q.setQuestionText(cols[0].trim());
        q.setOptionA(cols[1].trim());
        q.setOptionB(cols[2].trim());
        q.setOptionC(cols[3].trim());
        q.setOptionD(cols[4].trim());
        q.setCorrectAnswer(cols[5].trim());
        q.setCategory(cols.length > 6 ? cols[6].trim() : "");
        q.setDifficulty(cols.length > 7 ? cols[7].trim() : "");
        return q;
    }
}
