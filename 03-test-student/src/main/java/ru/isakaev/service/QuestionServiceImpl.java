package ru.isakaev.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import ru.isakaev.dao.QuestionDao;
import ru.isakaev.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation
 */
@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionDao questionDao;

    private Set<Question> questions = new HashSet<>();

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Set<Question> loadQuestions() {
        try(CSVReader reader = new CSVReader(new InputStreamReader(questionDao.getInputStream()))){
            reader.readAll().stream()
                    .map(e -> {
                        List<String> stringList = Arrays.asList(e);
                        String rightAnswer = stringList.stream()
                                .filter(a -> a.startsWith("*"))
                                .map(s -> s.substring(1)).findFirst().orElse("");
                        List<String> collect = stringList.stream()
                                .map(a -> a.startsWith("*")?a.substring(1):a)
                                .collect(Collectors.toList());
                        return new Question(collect.get(0),
                                            Arrays.copyOfRange(collect.toArray(new String[collect.size()]),1, collect.size()),
                                            rightAnswer);
                    }).forEach(question -> questions.add(question));
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return questions;
    }

}
