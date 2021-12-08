package ru.isakaev.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student("Ilnur", "Sakaev", 3);
    }

    @Test
    void shouldHaveCorrectConstructor() {
        assertThat(student.getFirstName()).isEqualTo("Ilnur");
    }

    @Test
    void setFirstName() {
        student.setFirstName("Ivan");
        assertThat(student.getFirstName()).isEqualTo("Ivan");
    }

    @Test
    void setLastName() {
        student.setLastName("Petrov");
        assertThat(student.getLastName()).isEqualTo("Petrov");
    }

    @Test
    void setAvailableAttempts() {
        student.setAvailableAttempts(5);
        assertThat(student.getAvailableAttempts()).isEqualTo(5);
    }

    @Test
    void setIsTestComplete() {
        student.setIsTestComplete(true);
        assertThat(student.getIsTestComplete()).isEqualTo(true);
    }
}