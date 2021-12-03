package ru.isakaev.dao;

import java.io.InputStream;

/**
 * Dao implementation
 */
public class QuestionDaoImpl implements QuestionDao {

    private final String fileName;


    public QuestionDaoImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public InputStream getInputStream() {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
        return resourceAsStream;
    }
}
