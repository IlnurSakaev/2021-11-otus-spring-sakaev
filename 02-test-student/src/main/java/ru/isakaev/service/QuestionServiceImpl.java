package ru.isakaev.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.isakaev.dao.QuestionDao;
import ru.isakaev.model.Student;
import ru.isakaev.model.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Service implementation
 */
@Service
@PropertySource("classpath:application.properties")
public class QuestionServiceImpl implements QuestionService{

    private QuestionDao questionDao;

    @Value("${attempt.count}")
    private Integer attemptCount;
    @Value("${passing.barrier}")
    private Integer passingBarrier;

    Set<Student> students = new HashSet<>();

    Set<Question> questions = new HashSet<>();

    Scanner scanner = new Scanner(System.in);

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public void loadQuestions() {
        try(CSVReader reader = new CSVReader(new InputStreamReader(questionDao.getInputStream()))){
            reader.readAll().stream()
                    .map(e -> {
                        List<String> stringList = Arrays.asList(e);
                        String rightAnswer = stringList.stream()
                                .filter(a -> a.startsWith("*"))
                                .map(s -> s.substring(1)).findFirst().orElse("");
                        List<String> collect = stringList.stream()
                                .map(a -> a.startsWith("*")?a.substring(1):a)
                                .collect(Collectors.toList());
                        return new Question(collect.get(0),
                                            Arrays.copyOfRange(collect.toArray(new String[collect.size()]),1, collect.size()),
                                            rightAnswer);
                    }).forEach(question -> questions.add(question));
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testStudent() {
        boolean testing = true;
        while(testing){

            Student student = getStudent();

            if (student.getIsTestComplete()){
                System.out.println("Student " + student.getFirstName() + " " + student.getLastName() + " successfully pass test.");
                continue;
            }

            if (!isStudentHasAttempts(student)){
             continue;
            }

            testing(student);

             System.out.println("Enter \"EXIT\" if want to exit test or any line for test another student");
            if (scanner.nextLine().equalsIgnoreCase("exit")){
                testing = false;
            }
        }
    }

    private boolean isStudentHasAttempts(Student student) {
        int attCount = student.getAvailableAttempts();
        if(attCount < 1){
            System.out.println("The Student " + student.getFirstName()
                    + " " + student.getLastName() + " has not available attempt");
            return false;
        }
        System.out.println(student.getFirstName() + " " + student.getLastName() + " your have " + attCount + " attempts" );
        return true;
    }

    private Student getStudent(){
        System.out.println("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter your last name: ");
        String lastName = scanner.nextLine();
        Student student = null;

        for (Student s: students){
            if (s.getFirstName().equalsIgnoreCase(firstName)&& s.getLastName().equalsIgnoreCase(lastName)){
                student = s;
                if (student.getAvailableAttempts() < 1){
                }
            }
        }
        if (student == null){
            student = new Student(firstName,lastName,attemptCount);
            students.add(student);
        }
        return student;
    }

    private void testing(Student student){
        int attCount = student.getAvailableAttempts();

        boolean oneMoreAttempt;
        do {
            oneMoreAttempt = false;
            int countOfRightAnswer = 0;
            for (Question q : questions) {
                System.out.println(customizeQuestionForPrint(q));
                if (scanner.nextLine().equalsIgnoreCase(q.getRightAnswer())) {
                    countOfRightAnswer++;
                }
            }
            if (countOfRightAnswer >= passingBarrier) {
                System.out.println("Eee you pass test and have " + countOfRightAnswer + " right answer!");
            } else {
                System.out.println(" you fail test and have " + countOfRightAnswer + " right answer, and have " + (--attCount) + " attempts");
                if (attCount > 0) {
                    System.out.println(" If you want try again enter YES");
                    if (scanner.nextLine().equalsIgnoreCase("YES")) {
                        oneMoreAttempt = true;
                    }

                }
            }
            student.setAvailableAttempts(attCount);
        }while (oneMoreAttempt);
    }


    private String customizeQuestionForPrint(Question question){

        StringBuilder builder = new StringBuilder();
        builder.append("Question : ").append(question.getTextQuestion()).append('?');
        String[] answers = question.getAnswers();
        for (int i=0; i<answers.length; i++) {
            builder.append('\n');
            builder.append(answers[i].trim());
        }
        builder.append('\n');
        return builder.toString();
    }

}
