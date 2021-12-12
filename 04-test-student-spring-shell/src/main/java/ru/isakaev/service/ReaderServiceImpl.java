package ru.isakaev.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ReaderServiceImpl implements ReaderService {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readFromConsole() {

        return scanner.nextLine();
    }
}
