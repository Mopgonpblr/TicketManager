package com.danliuk.logic;

import com.danliuk.model.BusTicket;

import java.time.LocalDate;

public class Validation {

    private int totalTickets;
    private int validTickets;
    private int startDateViolations;
    private int priceViolations;
    private int ticketTypeViolations;

    public void validate(BusTicket busTicket) {
        int violations = 0;

        /* Only DAY, WEEK and YEAR types must have a [start date] */
        if (busTicket.getTicketType() != null &&
                !busTicket.getTicketType().equals("DAY")
                && !busTicket.getTicketType().equals("WEEK")
                && !busTicket.getTicketType().equals("YEAR")
                && busTicket.getStartDate() != null
                && !busTicket.getStartDate().isBlank()) {
            System.out.println("Only DAY, WEEK and YEAR types must have a [start date]");
            this.startDateViolations++;
            violations++;
        }

        /* 2. [start date] can't be in the future */
        if (busTicket.getStartDate() != null
                && !busTicket.getStartDate().isBlank()) {
            LocalDate date = LocalDate.parse(busTicket.getStartDate());
            if (date.isAfter(LocalDate.now())) {
                System.out.println("Only DAY, WEEK and YEAR types must have a [start date]");
                this.startDateViolations++;
                violations++;
            }
        }

        /* 3. [ticket type] valid values are DAY, WEEK, MONTH, YEAR */
        if (busTicket.getTicketType() == null ||
                (!busTicket.getTicketType().equals("DAY")
                        && !busTicket.getTicketType().equals("WEEK")
                        && !busTicket.getTicketType().equals("MONTH")
                        && !busTicket.getTicketType().equals("YEAR"))) {
            System.out.println("[ticket type] valid values are DAY, WEEK, MONTH, YEAR");
            this.ticketTypeViolations++;
            violations++;
        }

        if (busTicket.getPrice() != null) {
            /* Price can't be zero */
            if (Integer.parseInt(busTicket.getPrice()) == 0) {
                System.out.println("Ticket price can't be 0");
                this.priceViolations++;
                violations++;
            }

            /* 4. [price] should always be even */
            if (Integer.parseInt(busTicket.getPrice()) % 2 != 0) {
                System.out.println("[price] should always be even");
                this.priceViolations++;
                violations++;
            }
        }
        else
        {
            System.out.println("[price] is null");
            this.priceViolations++;
            violations++;
        }

        if (violations == 0) {
            this.validTickets++;
        }
        this.totalTickets++;
    }

    public void output() {
        System.out.println("Total = " + totalTickets);
        System.out.println("Valid = " + validTickets);
        System.out.print("Most popular violation = ");
        if (this.startDateViolations > this.priceViolations
                && this.startDateViolations > this.ticketTypeViolations) {
            System.out.println("start date");
        } else if (this.priceViolations > this.startDateViolations
                && this.priceViolations > this.ticketTypeViolations) {
            System.out.println("price");
        } else if (this.ticketTypeViolations > this.startDateViolations
                && this.ticketTypeViolations > this.priceViolations) {
            System.out.println("ticket type");
        } else {
            System.out.println("equal");
        }
    }
}
