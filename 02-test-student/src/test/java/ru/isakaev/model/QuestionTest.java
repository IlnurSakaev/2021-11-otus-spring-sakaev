package ru.isakaev.model;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@DisplayName("Test QuestionTest")
class QuestionTest {

    private String[] values;
    private Question question;
    @BeforeEach
    void setUp() {
        values = new String[]{"One plus one", "one", "two", "*three"};
        question = new Question(values[0], Arrays.copyOfRange(values,1,values.length), values[2]);
    }

    @Test
    void shouldHaveCorrectConstructor() {
        Assertions.assertThat(question.getTextQuestion()).isEqualTo("One plus one");
    }

    @Test
    void setTextQuestion_void_success() {
        question.setTextQuestion("New question");
        Assertions.assertThat(question.getTextQuestion()).isEqualTo("New question");
    }

    @Test
    void setAnswers_void_success() {
        question.setAnswers(new String[]{"three", "four", "five"});
        Assertions.assertThat(question.getAnswers())
                .hasSize(3)
                .containsOnly("three", "four", "five");
    }

    @Test
    void setRightAnswer_void_success() {
        question.setRightAnswer("two");
        Assertions.assertThat(question.getRightAnswer()).isEqualTo("two");
    }
}