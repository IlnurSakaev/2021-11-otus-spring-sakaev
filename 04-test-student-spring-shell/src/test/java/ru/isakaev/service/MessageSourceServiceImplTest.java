package ru.isakaev.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;
import java.util.Locale;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
@TestPropertySource(properties = "location=ru")
class MessageSourceServiceImplTest {

    @Value( "${location}" )
    private String location;

    @MockBean
    private MessageSource source;

    @Test
    void getMessage() {
        MessageSourceService messageSource = new MessageSourceServiceImpl(location, source);

        when(source.getMessage("Имя поля", null, new Locale(location))).thenReturn("Значение поля");

        String message = messageSource.getMessage("Имя поля");

        String expectedAnswer = "Значение поля";

        assertThat(message).isEqualTo(expectedAnswer);
    }
}