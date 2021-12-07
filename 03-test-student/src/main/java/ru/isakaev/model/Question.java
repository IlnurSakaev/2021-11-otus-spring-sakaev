package ru.isakaev.model;

import lombok.Data;

/**
 * Model
 */
@Data
public class Question {

    private String textQuestion;

    private String[] answers;

    private String rightAnswer;

    public Question(String textQuestion, String[] answers, String rightAnswer) {
        this.textQuestion = textQuestion;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
    }
}
