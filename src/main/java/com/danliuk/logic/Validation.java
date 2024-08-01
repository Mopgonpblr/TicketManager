package com.danliuk.logic;

import com.danliuk.exceptions.PriceException;
import com.danliuk.exceptions.StartDateException;
import com.danliuk.exceptions.TicketTypeException;
import com.danliuk.model.BusTicket;

import java.time.LocalDate;

public class Validation {

    private int totalTickets = 0;
    private int validTickets = 0;
    private int startDateExceptions = 0;
    private int priceExceptions = 0;
    private int ticketTypeExceptions = 0;

    public void validate(BusTicket busTicket) {
        int exceptions = 0;

        try {
            validateTicketType(busTicket);
        } catch (TicketTypeException e) {
            System.out.println(e.getMessage());
            this.ticketTypeExceptions++;
            exceptions++;
        }

        try {
            validateStartDate(busTicket);
        } catch (StartDateException e) {
            System.out.println(e.getMessage());
            this.startDateExceptions++;
            exceptions++;
        }

        try {
            validatePrice(busTicket);
        } catch (PriceException e) {
            System.out.println(e.getMessage());
            this.priceExceptions++;
            exceptions++;
        }

        if (exceptions == 0) {
            this.validTickets++;
        }
        this.totalTickets++;
    }

    public static void validateStartDate(BusTicket busTicket)
            throws StartDateException {

        /* Only DAY, WEEK and YEAR types must have a [start date] */
        if (busTicket.getTicketType() != null &&
                !busTicket.getTicketType().equals("DAY")
                && !busTicket.getTicketType().equals("WEEK")
                && !busTicket.getTicketType().equals("YEAR")
                && busTicket.getStartDate() != null
                && !busTicket.getStartDate().isBlank()) {
            throw new StartDateException
                    ("Only DAY, WEEK and YEAR types must have a [start date]");

        }

        /* 2. [start date] can't be in the future */
        if (busTicket.getStartDate() != null
                && !busTicket.getStartDate().isBlank()) {
            LocalDate date = LocalDate.parse(busTicket.getStartDate());
            if (date.isAfter(LocalDate.now())) {
                throw new StartDateException
                        ("[start date] can't be in the future");
            }
        }
    }

    public static void validateTicketType(BusTicket busTicket)
            throws TicketTypeException {

        // 3. [ticket type] valid values are DAY, WEEK, MONTH, YEAR
        if (busTicket.getTicketType() == null ||
                (!busTicket.getTicketType().equals("DAY")
                        && !busTicket.getTicketType().equals("WEEK")
                        && !busTicket.getTicketType().equals("MONTH")
                        && !busTicket.getTicketType().equals("YEAR"))) {
            throw new TicketTypeException
                    ("[ticket type] valid values are DAY, WEEK, MONTH, YEAR");
        }
    }

    public static void validatePrice(BusTicket busTicket)
            throws PriceException {
        if (busTicket.getPrice() == null) {
            throw new PriceException("Ticket price is null");
        }

        /* Price can't be zero */
        if (Integer.parseInt(busTicket.getPrice()) == 0) {
            throw new PriceException("Ticket price can't be 0");
        }

        /* 4. [price] should always be even */
        if (Integer.parseInt(busTicket.getPrice()) % 2 != 0) {
            throw new PriceException("[price] should always be even");
        }
    }

    public void output() {
        System.out.println("Total = " + totalTickets);
        System.out.println("Valid = " + validTickets);
        System.out.print("Most popular violation = ");
        if (this.startDateExceptions > this.priceExceptions
                && this.startDateExceptions > this.ticketTypeExceptions) {
            System.out.println("start date");
        } else if (this.priceExceptions > this.startDateExceptions
                && this.priceExceptions > this.ticketTypeExceptions) {
            System.out.println("price");
        } else if (this.ticketTypeExceptions > this.startDateExceptions
                && this.ticketTypeExceptions > this.priceExceptions) {
            System.out.println("ticket type");
        } else {
            System.out.println("equal");
        }
    }
}
