package ru.isakaev.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.isakaev.model.Student;

import java.util.HashSet;
import java.util.Set;

@Service

@PropertySource("classpath:application.yml")
public class StudentServiceImpl implements StudentService {

    private final Integer attemptCount;

    private final MessageSourceService source;

    private Set<Student> students = new HashSet<>();

    private final ReaderService readerService;

    public StudentServiceImpl( @Value("${attempt.count}")Integer attemptCount, MessageSourceService source, ReaderService readerService) {
        this.attemptCount = attemptCount;
        this.source = source;
        this.readerService = readerService;
    }

    @Override
    public Student getStudent(){
        System.out.println(source.getMessage("text.name"));
        String firstName = readerService.readFromConsole();

        System.out.println(source.getMessage("text.lastname"));
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
