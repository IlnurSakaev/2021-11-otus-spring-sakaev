package ru.isakaev.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Locale;


@SpringBootTest(classes = QuestionDaoImpl.class)
@TestPropertySource(properties = "location=ru")
class QuestionDaoImplTest {

    @Value( "${location}" )
    private String location;

    @MockBean
    private MessageSource source;

    @Test
    void getFile_void_success() throws NoSuchFieldException, IllegalAccessException {

        // Корректно ли так создавать с учетом того что мы подставляем Mock? Autowired ведь тут не сработает
        QuestionDao questionDao = new QuestionDaoImpl(source);


        Mockito.when(source.getMessage("file.path", null, new Locale(location)))
                .thenReturn("file.csv");

        Field location = questionDao.getClass().getDeclaredField("location");
            location.setAccessible(true);
            location.set(questionDao, "ru");

            InputStream inputStream = questionDao.getInputStream();

        Assertions.assertThat(inputStream).isNotNull();
    }

}