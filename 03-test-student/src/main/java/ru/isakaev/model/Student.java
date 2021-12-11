package ru.isakaev.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Student {

    private final String firstName;
    private final String lastName;
    private Integer availableAttempts;
    private Boolean isTestComplete = false;

    public Student(String firstName, String lastName, Integer availableAttempts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.availableAttempts = availableAttempts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
