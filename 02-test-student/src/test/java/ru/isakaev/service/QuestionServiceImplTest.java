package ru.isakaev.service;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ru.isakaev.dao.QuestionDao;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class QuestionServiceImplTest {


    @Mock
    private static QuestionDao dao;

    public QuestionServiceImplTest() {
    }

    @Before
    static void beforeAll() {
        InputStream inputStream =
            Thread.currentThread().getContextClassLoader().getResourceAsStream("file.csv");
        when(dao.getInputStream()).thenReturn(inputStream);
    }

    @Test
    void printQuestions() throws URISyntaxException {


    }
}