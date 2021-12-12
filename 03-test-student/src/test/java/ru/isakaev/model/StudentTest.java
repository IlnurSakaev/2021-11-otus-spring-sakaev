package ru.isakaev.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

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
    void setFirstName() throws NoSuchFieldException, IllegalAccessException {
        Field firstName = student.getClass().getDeclaredField("firstName");
        firstName.setAccessible(true);
        firstName.set(student,"Ivan");
        assertThat(student.getFirstName()).isEqualTo("Ivan");
    }

    @Test
    void setLastName() throws NoSuchFieldException, IllegalAccessException {
        Field lastName = student.getClass().getDeclaredField("lastName");
        lastName.setAccessible(true);
        lastName.set(student,"Petrov");
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