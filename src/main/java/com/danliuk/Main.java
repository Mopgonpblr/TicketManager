package com.danliuk;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Scanner;

import static com.danliuk.logic.Parsing.*;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {

        Scanner scanner = new Scanner(System.in);
        String option;

        do {
            System.out.println("Choose the reading option:");
            System.out.println("1. From console");
            System.out.println("2. From file");
            option = scanner.nextLine();
        } while (!option.equals("1") && !option.equals("2"));

        if (option.equals("1")) {
            parseByConsole();
        }
        else {
            parseByFile();
        }
    }
}