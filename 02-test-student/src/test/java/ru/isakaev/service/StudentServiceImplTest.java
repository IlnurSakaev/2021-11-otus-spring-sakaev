package ru.isakaev.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.isakaev.model.Student;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class StudentServiceImplTest {

    @Test
    void getStudent() {
        StudentServiceImpl studentService = new StudentServiceImpl();
        Student testStudent = new Student("Ilnur", "Sakaev", 3);
        InputStream stdin = System.in;

        try {
            studentService.setAttemptCount(3);
            studentService.setScanner(new ByteArrayInputStream("Ilnur\nSakaev".getBytes()));
            Student student = studentService.getStudent();
            Assertions.assertThat(student.equals(testStudent));
        } finally {
            System.setIn(stdin);
        }

    }
}