package ru.isakaev.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.isakaev.model.Student;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Scanner;

class StudentServiceImplTest {

    @Test
    void getStudent() throws NoSuchFieldException, IllegalAccessException {
        StudentServiceImpl studentService = new StudentServiceImpl();
        Student testStudent = new Student("Ilnur", "Sakaev", 3);
        InputStream stdin = System.in;

        Field attemptCount = studentService.getClass().getDeclaredField("attemptCount");
        attemptCount.setAccessible(true);
        attemptCount.set(studentService, 3);

        Field scanner = studentService.getClass().getDeclaredField("scanner");
        scanner.setAccessible(true);
        scanner.set(studentService, new Scanner(new ByteArrayInputStream("Ilnur\nSakaev".getBytes())));

        try {
            Student student = studentService.getStudent();
            Assertions.assertThat(student.equals(testStudent));
        } finally {
            System.setIn(stdin);
        }

    }
}