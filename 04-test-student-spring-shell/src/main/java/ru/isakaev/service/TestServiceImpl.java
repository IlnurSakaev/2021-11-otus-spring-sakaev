package ru.isakaev.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.isakaev.model.Question;
import ru.isakaev.model.Student;

import java.util.HashSet;
import java.util.Set;

@Service
@PropertySource("classpath:application.yml")
public class TestServiceImpl implements TestService {

    private final Integer passingBarrier;

    private final MessageSourceService source;

    private final StudentService studentService;

    private final QuestionService questionService;

    private final ReaderService readerService;

    private Set<Question> questions = new HashSet<>();

    public TestServiceImpl(@Value("${passing.barrier}")Integer passingBarrier, MessageSourceService source,
                           StudentService studentService, QuestionService questionService, ReaderService readerService) {
        this.passingBarrier = passingBarrier;
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
                System.out.printf(source.getMessage("text.test.pass"), student.getFirstName(), student.getLastName(), System.lineSeparator());
                testing = false;
                continue;
            }

            if (!isStudentHasAttempts(student)){
                testing = false;
                continue;
            }
            checkQuestions();

            survey(student);

            System.out.println(source.getMessage("text.test.exit"));
            String checkExit = readerService.readFromConsole();
            if (checkExit.equalsIgnoreCase(source.getMessage("text.test.check"))){
                testing = false;
            }
        }
    }

    private boolean isStudentHasAttempts(Student student) {
        int attCount = student.getAvailableAttempts();
        if(attCount < 1){
            System.out.printf(source.getMessage("text.attempt.empty"), student.getFirstName(), student.getLastName(), System.lineSeparator());
            return false;
        }
        System.out.printf(source.getMessage("text.attempt.exist"), student.getFirstName(), student.getLastName(), attCount, System.lineSeparator());
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
                System.out.printf(source.getMessage("text.test.finish.pass"), countOfRightAnswer, System.lineSeparator());
                student.setIsTestComplete(true);
            } else {
                System.out.printf(source.getMessage("text.test.finish.fail"), countOfRightAnswer, (--attCount),  System.lineSeparator());
                if (attCount > 0) {
                    System.out.println(source.getMessage("text.test.finish.retry"));
                    String checkYes = readerService.readFromConsole();
                    String yesWord = source.getMessage("text.test.finish.check");
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
        builder.append(source.getMessage("text.question")).append(" : ").append(question.getTextQuestion()).append('?');
        String[] answers = question.getAnswers();
        for (int i=0; i<answers.length; i++) {
            builder.append('\n');
            builder.append(answers[i].trim());
        }
        builder.append('\n');
        return builder.toString();
    }
}
