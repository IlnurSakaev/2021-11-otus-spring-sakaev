package ru.isakaev.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;

@DisplayName("Test QuestionDaoImplTest")
class QuestionDaoImplTest {

    private final String fileName = "file.csv";

    @Test
    void getFile_void_success() {
        QuestionDaoImpl questionDao = new QuestionDaoImpl(fileName);
        InputStream inputStream = questionDao.getInputStream();

        Assertions.assertThat(inputStream).isNotNull();
    }
}