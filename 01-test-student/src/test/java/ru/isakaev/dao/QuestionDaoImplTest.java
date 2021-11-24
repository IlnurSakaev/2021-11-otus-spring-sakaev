package ru.isakaev.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

class QuestionDaoImplTest {

    private static final String FILE_NAME = "file.csv";

    @Test
    void getFile_void_success() {
        QuestionDaoImpl questionDao = new QuestionDaoImpl(FILE_NAME);
        File file = questionDao.getFile();

        Assertions.assertThat(file).exists();
    }
}