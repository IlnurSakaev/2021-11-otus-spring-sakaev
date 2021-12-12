package ru.isakaev.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import java.util.Locale;

@Service
public class MessageSourceServiceImpl implements MessageSourceService {

    private final Locale locale;

    private final MessageSource source;

    public MessageSourceServiceImpl(@Value("${lang.location}") String location, MessageSource source) {
        this.locale = new Locale(location);
        this.source = source;
    }

    @Override
    public String getMessage(String fieldName) {
        return source.getMessage(fieldName, null, locale);
    }
}
