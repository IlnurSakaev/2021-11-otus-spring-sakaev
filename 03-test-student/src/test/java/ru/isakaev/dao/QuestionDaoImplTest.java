package ru.isakaev.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(properties = "location=ru")
class QuestionDaoImplTest {

    @Value( "${location}" )
    private String location;

    @Test
    void getFile_void_success() throws NoSuchFieldException, IllegalAccessException {
        MessageSource source = Mockito.mock(MessageSource.class);
        Mockito.when(source.getMessage("file.path", null, new Locale(location)))
                .thenReturn("file.csv");
        QuestionDaoImpl questionDao = new QuestionDaoImpl(source);

        Field location = questionDao.getClass().getDeclaredField("location");
            location.setAccessible(true);
            location.set(questionDao, "ru");
        InputStream inputStream = questionDao.getInputStream();
        System.out.println(inputStream);

        Assertions.assertThat(inputStream).isNotNull();
    }

}