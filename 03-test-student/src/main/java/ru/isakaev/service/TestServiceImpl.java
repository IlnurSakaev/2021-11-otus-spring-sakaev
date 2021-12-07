package ru.isakaev.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.isakaev.model.Question;
import ru.isakaev.model.Student;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
@PropertySource("classpath:application.yml")
public class TestServiceImpl implements TestService {

    @Value("${passing.barrier}")
    private Integer passingBarrier;

    @Value( "${lang.location}" )
    private String location;

    private final MessageSource source;

    private final StudentService studentService;

    private final QuestionService questionService;

    private final ReaderService readerService;


    private Set<Question> questions = new HashSet<>();

    public TestServiceImpl(MessageSource source, StudentService studentService, QuestionService questionService, ReaderService readerService) {
        this.source = source;
        this.studentService = studentService;
        this.questionService = questionService;
        this.readerService = readerService;
    }

    @Override
    public void testStudent() {
        boolean testing = true;
        while(testing){

            Student student = studentService.getStudent();

            if (student.getIsTestComplete()){
                System.out.printf(source.getMessage("text.test.pass", null, new Locale(location)), student.getFirstName(), student.getLastName(), System.lineSeparator());
                testing = false;
                continue;
            }

            if (!isStudentHasAttempts(student)){
                testing = false;
                continue;
            }
            checkQuestions();

            survey(student);

            System.out.println(source.getMessage("text.test.exit", null, new Locale(location)));
            String checkExit = readerService.readFromConsole();
            if (checkExit.equalsIgnoreCase(source.getMessage("text.test.check", null, new Locale(location)))){
                testing = false;
            }
        }
    }

    private boolean isStudentHasAttempts(Student student) {
        int attCount = student.getAvailableAttempts();
        if(attCount < 1){
            System.out.printf(source.getMessage("text.attempt.empty", null, new Locale(location)), student.getFirstName(), student.getLastName(), System.lineSeparator());
            return false;
        }
        System.out.printf(source.getMessage("text.attempt.exist", null, new Locale(location)), student.getFirstName(), student.getLastName(), attCount, System.lineSeparator());
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
                if (readerService.readFromConsole().equalsIgnoreCase(q.getRightAnswer())) {
                    countOfRightAnswer++;
                }
            }
            if (countOfRightAnswer >= passingBarrier) {
                System.out.printf(source.getMessage("text.test.finish.pass", null, new Locale(location)), countOfRightAnswer, System.lineSeparator());
                student.setIsTestComplete(true);
            } else {
                System.out.printf(source.getMessage("text.test.finish.fail", null, new Locale(location)), countOfRightAnswer, (--attCount),  System.lineSeparator());
                if (attCount > 0) {
                    System.out.println(source.getMessage("text.test.finish.retry", null, new Locale(location)));
                    String checkYes = readerService.readFromConsole();
                    String yesWord = source.getMessage("text.test.finish.check", null, new Locale(location));
                    if (checkYes.equalsIgnoreCase(yesWord)) {
                        oneMoreAttempt = true;
                    }
                }
            }
            student.setAvailableAttempts(attCount);
        }while (oneMoreAttempt);
    }

    private String customizeQuestionForPrint(Question question){

        StringBuilder builder = new StringBuilder();
        builder.append(source.getMessage("text.question", null, new Locale(location))).append(" : ").append(question.getTextQuestion()).append('?');
        String[] answers = question.getAnswers();
        for (int i=0; i<answers.length; i++) {
            builder.append('\n');
            builder.append(answers[i].trim());
        }
        builder.append('\n');
        return builder.toString();
    }
}
