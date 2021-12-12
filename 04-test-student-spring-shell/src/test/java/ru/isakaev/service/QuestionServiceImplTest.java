package ru.isakaev.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import ru.isakaev.dao.QuestionDao;
import ru.isakaev.model.Question;
import java.util.Set;

import static org.mockito.Mockito.when;


@SpringBootTest
class QuestionServiceImplTest {

    @MockBean
    private QuestionDao dao;

    @Configuration
    static class QuestionConfiguration{

    }

    @Test
    void printQuestions(){
        when(dao.getInputStream()).thenReturn(ClassLoader.getSystemClassLoader().getResourceAsStream("file.csv"));

        QuestionService questionService = new QuestionServiceImpl(dao);

        Set<Question> questions = questionService.loadQuestions();

        Assertions.assertThat(questions.size()).isEqualTo(8);

    }
}