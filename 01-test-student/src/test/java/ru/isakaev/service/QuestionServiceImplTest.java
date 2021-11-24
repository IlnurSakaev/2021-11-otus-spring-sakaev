package ru.isakaev.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.isakaev.dao.QuestionDao;

import java.io.File;
import java.net.URISyntaxException;

import static org.mockito.BDDMockito.given;

class QuestionServiceImplTest {


    @Mock
    private QuestionDao dao;

    @Test
    void printQuestions() throws URISyntaxException {

        QuestionServiceImpl questionService = new QuestionServiceImpl(dao);
        given(dao.getFile()).willReturn(new File(ClassLoader.getSystemResource("file.csv").toURI()));

        questionService.printQuestions();
        Mockito.verify(dao.getFile(),Mockito.times(1));

    }
}