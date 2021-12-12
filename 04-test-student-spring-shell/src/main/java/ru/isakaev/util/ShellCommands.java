package ru.isakaev.util;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.isakaev.service.TestService;

//@ShellComponent
public class ShellCommands {

    private final TestService testService;

    public ShellCommands(TestService testService) {
        this.testService = testService;
    }
    @ShellMethod("Start test")
    public void hello(
//            @ShellOption String name
    ){
        testService.testStudent();
    }
}
