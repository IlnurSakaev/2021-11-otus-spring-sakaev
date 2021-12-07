package ru.isakaev.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.isakaev.dao.QuestionDao;
import ru.isakaev.dao.QuestionDaoImpl;
import ru.isakaev.model.Question;
import java.net.URISyntaxException;
import java.util.Set;


@SpringBootTest
class QuestionServiceImplTest {


    @Test
    void printQuestions(){
        QuestionDao dao = Mockito.mock(QuestionDaoImpl.class);
        Mockito.when(dao.getInputStream()).thenReturn(ClassLoader.getSystemClassLoader().getResourceAsStream("file.csv"));

        QuestionService questionService = new QuestionServiceImpl(dao);

        Set<Question> questions = questionService.loadQuestions();

        Assertions.assertThat(questions.size()).isEqualTo(8);

    }
}