package ru.isakaev.util;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.isakaev.service.TestService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final TestService testService;

    @ShellMethod(value = "Start Test", key = {"start"})
    public String hello(){
        testService.testStudent();
        return "Application end";
    }
}
