package ru.isakaev.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.isakaev.model.Question;
import ru.isakaev.model.Student;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Service
public class TestServiceImpl implements TestService {

    @Value("${passing.barrier}")
    private Integer passingBarrier;

    private StudentService studentService;

    private QuestionService questionService;

    Set<Question> questions = new HashSet<>();

    Scanner scanner = new Scanner(System.in);

    public TestServiceImpl(StudentService studentService, QuestionService questionService) {
        this.studentService = studentService;
        this.questionService = questionService;
    }

    @Override
    public void testStudent() {
        boolean testing = true;
        while(testing){

            Student student = studentService.getStudent();

            if (student.getIsTestComplete()){
                System.out.println("Student " + student.getFirstName() + " " + student.getLastName() + " successfully pass test.");
                continue;
            }

            if (!isStudentHasAttempts(student)){
                continue;
            }
            checkQuestions();

            survey(student);

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

    private void checkQuestions() {
        if (questions.isEmpty()){
            questions = questionService.loadQuestions();
        }
    }

    private void survey(Student student){
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
