package ru.isakaev;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.isakaev.service.TestService;

@ComponentScan
@Configuration
public class Main {


    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        TestService service = context.getBean(TestService.class);
        service.testStudent();
    }
}
