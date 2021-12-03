package ru.isakaev.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.isakaev.dao.QuestionDao;
import ru.isakaev.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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
        try(CSVReader reader = new CSVReader(new InputStreamReader(questionDao.getInputStream()))){
            reader.readAll().stream()
                    .map(e -> new Question(e[0], Arrays.copyOfRange(e,1, e.length)))
                    .forEach(question -> System.out.println(customizeQuestionForPrint(question)));
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private String customizeQuestionForPrint(Question question){

        StringBuilder builder = new StringBuilder();
        builder.append("Question : ").append(question.getTextQuestion()).append('?');
        String[] answers = question.getAnswers();
        for (int i=0; i<answers.length; i++) {
            builder.append('\n');
            builder.append("Option ").append(i + 1).append(": ");
            builder.append(answers[i].trim());
        }
        builder.append('\n');
        return builder.toString();
    }
}
