package ru.isakaev.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Scanner;



@SpringBootTest(classes = ReaderServiceImpl.class)
class ReaderServiceImplTest {

    @Autowired
    private ReaderService readerService;

    @Test
    void readFromConsole() {
        InputStream stdin = System.in;

        try {
            Field scanner = readerService.getClass().getDeclaredField("scanner");
            scanner.setAccessible(true);
            System.setIn(new ByteArrayInputStream("Ilnur\nSakaev".getBytes()));
            scanner.set(readerService, new Scanner(System.in));
            String s = readerService.readFromConsole();
            Assertions.assertThat(s.equals("Ilnur"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            System.setIn(stdin);
        }
    }
}