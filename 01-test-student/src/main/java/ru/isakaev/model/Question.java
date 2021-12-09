package ru.isakaev.model;

import lombok.Data;

/**
 * Model
 */
@Data
public class Question {

    private String textQuestion;

    private String[] answers;

    public Question(String textQuestion, String[] answers) {
        this.textQuestion = textQuestion;
        this.answers = answers;
    }
}
