package ru.isakaev.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.isakaev.model.Student;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Service
@PropertySource("classpath:application.properties")
public class StudentServiceImpl implements StudentService {

    @Value("${attempt.count}")
    private Integer attemptCount;

    private Set<Student> students = new HashSet<>();

    private Scanner scanner = new Scanner(System.in);

    @Override
    public Student getStudent(){
        System.out.println("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter your last name: ");
        String lastName = scanner.nextLine();
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

    public void setAttemptCount(Integer attemptCount) {
        this.attemptCount = attemptCount;
    }

    public void setScanner(InputStream scanner) {
        this.scanner = new Scanner(scanner);
    }
}
