package ru.isakaev.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;

/**
 * Dao implementation
 */
@Repository
@PropertySource("classpath:application.properties")
public class QuestionDaoImpl implements QuestionDao {

    private final String fileName;


    public QuestionDaoImpl(@Value("${file.path}") String fileName) {
        this.fileName = fileName;;
    }


    @Override
    public InputStream getInputStream() {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
        return resourceAsStream;
    }
}
