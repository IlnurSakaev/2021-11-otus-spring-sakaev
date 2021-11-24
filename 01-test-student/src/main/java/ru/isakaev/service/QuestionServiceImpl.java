package ru.isakaev.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.isakaev.dao.QuestionDao;
import ru.isakaev.model.Question;

import java.io.FileReader;
import java.io.IOException;

/**
 * Service implementation
 */
public class QuestionServiceImpl implements QuestionService{

    private QuestionDao questionDao;

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public void printQuestions() {
        try(CSVReader reader = new CSVReader(new FileReader(questionDao.getFile()))){
            reader.readAll().stream().map(Question::new).forEach(System.out::println);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}
