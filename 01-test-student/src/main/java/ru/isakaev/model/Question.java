package ru.isakaev.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Model
 */
public class Question {

    private String textQuestion;

    private String[] answers;

    public Question(String[] strings) {
        this.textQuestion = strings[0];
        answers = new String[strings.length - 1];
        for (int i = 0; i< answers.length; i++) {
            answers[i] = strings[i+1];
        }
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(textQuestion, question.textQuestion) && Arrays.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textQuestion, answers);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Question : ").append(textQuestion).append('?');
        for (int i=0; i<answers.length; i++) {
            builder.append('\n');
            builder.append("Option ").append(i + 1).append(": ");
            builder.append(answers[i].trim());
        }
        builder.append('\n');
        return builder.toString();
    }
}
