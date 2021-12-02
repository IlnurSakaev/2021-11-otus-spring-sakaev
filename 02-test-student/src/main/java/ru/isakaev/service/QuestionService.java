package ru.isakaev.service;

import ru.isakaev.model.Question;

import java.util.Set;

/**
 * Service
 */
public interface QuestionService {

        Set<Question> loadQuestions();

}
