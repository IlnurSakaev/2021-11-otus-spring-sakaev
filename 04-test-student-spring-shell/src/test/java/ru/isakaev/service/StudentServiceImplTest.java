package ru.isakaev.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import ru.isakaev.model.Student;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest(properties = "spring.shell.interactive.enabled=false")
@TestPropertySource(properties = {"count=7"})
class StudentServiceImplTest {

    @Value("${count}")
    private Integer attemptCount;

    @MockBean
    private MessageSourceService source;

    @MockBean
    private ReaderService readerService;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        when(source.getMessage("text.name"))
                .thenReturn("Enter your first name");

        when(source.getMessage("text.lastname"))
                .thenReturn("Enter your last name");

        String s = "Ilnur";
        String b = "Sakaev";

        when(readerService.readFromConsole()).thenReturn(s,b);
    }

    @Test
    void getStudent_student_success() throws NoSuchFieldException, IllegalAccessException {

        studentService = new StudentServiceImpl(attemptCount, source, readerService);

        //        attemptCount
        Field attemptCount = studentService.getClass().getDeclaredField("attemptCount");
        attemptCount.setAccessible(true);
        attemptCount.set(studentService, this.attemptCount);


        Student testStudent = new Student("Ilnur", "Sakaev", this.attemptCount);

        Student student = studentService.getStudent();

        Assertions.assertThat(student).isEqualTo(testStudent);
    }

    @Test
    void getStudent_student_exist_success() throws NoSuchFieldException, IllegalAccessException {

        studentService = new StudentServiceImpl(attemptCount, source, readerService);

        // add test student to Set
        Student testStudent = new Student("Ilnur", "Sakaev", 5);
        testStudent.setIsTestComplete(true);
        Set<Student> studentSet = new HashSet<>();
        studentSet.add(testStudent);

        Field students = studentService.getClass().getDeclaredField("students");
        students.setAccessible(true);
        students.set(studentService, studentSet);

        Student student = studentService.getStudent();

        Assertions.assertThat(student == testStudent).isEqualTo(true);


    }
}