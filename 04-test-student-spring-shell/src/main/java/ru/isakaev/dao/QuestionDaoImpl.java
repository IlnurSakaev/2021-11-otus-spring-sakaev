package ru.isakaev.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import ru.isakaev.logging.LogAnnotation;
import ru.isakaev.util.YamlFactory;

import java.io.InputStream;
import java.util.Locale;

/**
 * Dao implementation
 */
@Repository
@PropertySource(value = "classpath:application.yml", factory = YamlFactory.class)
public class QuestionDaoImpl implements QuestionDao {

    @Value( "${lang.location}" )
    private String location;

    private MessageSource source;



    public QuestionDaoImpl(@Qualifier("messageSource")MessageSource source) {
        this.source = source;
    }

    @Override
    @LogAnnotation
    public InputStream getInputStream() {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(source.getMessage("file.path", null, new Locale(location)));
        return resourceAsStream;
    }

}
