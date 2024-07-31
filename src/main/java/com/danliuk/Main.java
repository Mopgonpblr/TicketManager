package com.danliuk;

import com.danliuk.exceptions.PriceException;
import com.danliuk.exceptions.StartDateException;
import com.danliuk.exceptions.TicketTypeException;
import com.danliuk.model.BusTicket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {

        int x = 0;
        int validTickets = 0;
        int startDateExceptions = 0;
        int priceExceptions = 0;
        int ticketTypeExceptions = 0;

        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("Choose the reading option:");
            System.out.println("1. From console");
            System.out.println("2. From file");
            option = scanner.nextInt();
        } while (option != 1 && option != 2);

        if (option == 1) {
            do {
                String input = getInput();
                BusTicket busTicket = new ObjectMapper().readValue(input, BusTicket.class);

                // ticket validation
                //Show an error in the console if 1+ validation rule is violated
                try {
                    validate(busTicket);
                    validTickets++;
                } catch (StartDateException e) {
                    System.out.println(e.getMessage());
                    startDateExceptions++;

                } catch (PriceException e) {
                    System.out.println(e.getMessage());
                    priceExceptions++;
                } catch (TicketTypeException e) {
                    System.out.println(e.getMessage());
                    ticketTypeExceptions++;
                }


                System.out.println(busTicket.toString());
                x++;

            } while (x < 5);
        }
        //1. An ability to read tickets from a file
        if (option == 2) {
            try {
                scanner = new Scanner(new File("src/main/resources/ticketData.txt"));
                while (scanner.hasNextLine()) {
                    BusTicket busTicket = new ObjectMapper().readValue(scanner.nextLine(), BusTicket.class);
                    try {
                        validate(busTicket);
                        validTickets++;
                    } catch (StartDateException e) {
                        System.out.println(e.getMessage());
                        startDateExceptions++;
                    } catch (PriceException e) {
                        System.out.println(e.getMessage());
                        priceExceptions++;
                    } catch (TicketTypeException e) {
                        System.out.println(e.getMessage());
                        ticketTypeExceptions++;
                    }

                    System.out.println(busTicket.toString());
                    x++;
                }

                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        //The output
        System.out.println("Total = " + x);
        System.out.println("Valid = " + validTickets);
        System.out.print("Most popular violation = ");
        if (startDateExceptions > priceExceptions && startDateExceptions > ticketTypeExceptions) {
            System.out.println("start date");
        } else if (priceExceptions > startDateExceptions && priceExceptions > ticketTypeExceptions) {
            System.out.println("price");
        } else {
            System.out.println("ticket type");
        }
    }

    private static String getInput() {
        return new Scanner(System.in).nextLine();
    }

    //Create a function to validate Bus ticket.
    private static void validate(BusTicket busTicket) throws PriceException, StartDateException, TicketTypeException {
        // Only DAY, WEEK and YEAR types must have a [start date]
        // 3. [ticket type] valid values are DAY, WEEK, MONTH, YEAR
        if (busTicket.getTicketType() == null) {
            throw new TicketTypeException("The ticket type is null");
        }
        if (!busTicket.getTicketType().equals("DAY")
                && !busTicket.getTicketType().equals("WEEK")
                && !busTicket.getTicketType().equals("YEAR")) {
            if (busTicket.getStartDate() != null) {
                throw new StartDateException("Only DAY, WEEK and YEAR types must have a [start date]");
            }
            if (!busTicket.getTicketType().equals("MONTH")) {
                throw new TicketTypeException("[ticket type] valid values are DAY, WEEK, MONTH, YEAR");
            }
        }
        //2. [start date] can't be in the future

        if (busTicket.getStartDate() != null && !busTicket.getStartDate().isBlank()) {
            LocalDate date = LocalDate.parse(busTicket.getStartDate());
            if (date.isAfter(LocalDate.now())) {
                throw new StartDateException("[start date] can't be in the future");
            }
        }

        if (busTicket.getPrice() == null) {
            throw new PriceException("Ticket price is null");
        }
        //Price can't be zero
        if (Integer.parseInt(busTicket.getPrice()) == 0) {
            throw new PriceException("Ticket price can't be 0");
        }
        // 4. [price] should always be even
        if (Integer.parseInt(busTicket.getPrice()) % 2 != 0) {
            throw new PriceException("[price] should always be even");
        }

    }

}