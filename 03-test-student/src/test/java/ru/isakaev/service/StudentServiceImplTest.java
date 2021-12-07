package ru.isakaev.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.Assert;
import ru.isakaev.dao.QuestionDaoImpl;
import ru.isakaev.model.Student;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Locale;

@SpringBootTest
@TestPropertySource(properties = {"location=ru","count=3"})
class StudentServiceImplTest {

    @Value( "${location}" )
    private String location;

    @Value("${count}")
    private Integer attemptCount;

    @Test
    void getStudent() throws NoSuchFieldException, IllegalAccessException {
        MessageSource source = Mockito.mock(MessageSource.class);
        Mockito.when(source.getMessage("text.name", null, new Locale(location)))
                .thenReturn("Enter your first name");

        Mockito.when(source.getMessage("text.lastname", null, new Locale(location)))
                .thenReturn("Enter your last name");

        String s = "Ilnur";
        String b = "Sakaev";
        ReaderService readerService = Mockito.mock(ReaderService.class);
        Mockito.when(readerService.readFromConsole()).thenReturn(s,b);

        StudentService studentService = new StudentServiceImpl(source, readerService);
        //        location
        Field location = studentService.getClass().getDeclaredField("location");
        location.setAccessible(true);
        location.set(studentService, this.location);
        //        attemptCount
        Field attemptCount = studentService.getClass().getDeclaredField("attemptCount");
        attemptCount.setAccessible(true);
        attemptCount.set(studentService, this.attemptCount);


        Student testStudent = new Student("Ilnur", "Sakaev", 3);

        Student student = studentService.getStudent();

//        Assert.isTrue(true, student.equals(testStudent));
        Assertions.assertThat(student).isEqualTo(testStudent);



//        studentService.setAttemptCount(3);
//
//
//        InputStream stdin = System.in;
//
//        try {
//            studentService.setAttemptCount(3);
//            studentService.setScanner(new ByteArrayInputStream("Ilnur\nSakaev".getBytes()));
//            Student student = studentService.getStudent();
//            Assertions.assertThat(student.equals(testStudent));
//        } finally {
//            System.setIn(stdin);
//        }

    }
}