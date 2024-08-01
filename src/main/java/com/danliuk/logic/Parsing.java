package com.danliuk.logic;

import com.danliuk.model.BusTicket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parsing {

    public static void parseByConsole() throws JsonProcessingException{
        Validation val = new Validation();
        int x = 0;
        do {
            String input = new Scanner(System.in).nextLine();
            BusTicket busTicket = new ObjectMapper().readValue(input, BusTicket.class);

            // ticket validation
            //Show an error in the console if 1+ validation rule is violated
            val.validate(busTicket);

            System.out.println(busTicket.toString());
            x++;

        } while (x < 5);

        val.output();
    }

    /*1. An ability to read tickets from a file */
    public static void parseByFile() throws JsonProcessingException{
        Validation val = new Validation();
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/ticketData.txt"));
            while (scanner.hasNextLine()) {
                BusTicket busTicket = new ObjectMapper().readValue(scanner.nextLine(), BusTicket.class);
                val.validate(busTicket);
                System.out.println(busTicket.toString());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        val.output();
    }
}
