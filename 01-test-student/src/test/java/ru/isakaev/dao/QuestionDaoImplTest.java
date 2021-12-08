package ru.isakaev.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

class QuestionDaoImplTest {

    private static final String FILE_NAME = "file.csv";

    @Test
    void getFile_void_success() {
        QuestionDaoImpl questionDao = new QuestionDaoImpl(FILE_NAME);
        InputStream inputStream = questionDao.getInputStream();

        Assertions.assertThat(inputStream).isNotEmpty();
    }
}