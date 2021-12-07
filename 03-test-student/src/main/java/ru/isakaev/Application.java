package ru.isakaev;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.isakaev.service.TestService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
		TestService service = context.getBean(TestService.class);
		service.testStudent();
	}

}
