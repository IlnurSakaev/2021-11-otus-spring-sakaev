package ru.isakaev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.isakaev.model.Student;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service

@PropertySource("classpath:application.yml")
public class StudentServiceImpl implements StudentService {

    @Value("${attempt.count}")
    private Integer attemptCount;

    @Value( "${lang.location}" )
    private String location;

    private final MessageSource source;

    private Set<Student> students = new HashSet<>();

    private final ReaderService readerService;

    public StudentServiceImpl(@Qualifier("messageSource")MessageSource source, ReaderService readerService) {
        this.source = source;
        this.readerService = readerService;
    }

    @Override
    public Student getStudent(){
        System.out.println(source.getMessage("text.name", null, new Locale(location)));
        String firstName = readerService.readFromConsole();

        System.out.println(source.getMessage("text.lastname", null, new Locale(location)));
        String lastName =  readerService.readFromConsole();
        Student student = null;

        for (Student s: students){
            if (s.getFirstName().equalsIgnoreCase(firstName)&& s.getLastName().equalsIgnoreCase(lastName)){
                student = s;
            }
        }

        if (student == null){
            student = new Student(firstName,lastName,attemptCount);
            students.add(student);
        }

        return student;
    }
}
