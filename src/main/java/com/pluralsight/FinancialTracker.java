package com.pluralsight;

import java.io.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Capstone skeleton – personal finance tracker.
 * ------------------------------------------------
 * File format  (pipe-delimited)
 *     yyyy-MM-dd|HH:mm:ss|description|vendor|amount
 * A deposit has a positive amount; a payment is stored
 * as a negative amount.
 *
 * TODO: create the Transaction class in this package first.
 *       One object = one line of the file. Decide its fields from
 *       the file format above, add a constructor and getters.
 *       This class does not compile until Transaction exists.
 */
public class FinancialTracker {

    /* ------------------------------------------------------------------
       Shared data and formatters
       ------------------------------------------------------------------ */
    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern(TIME_PATTERN);
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    public static void main(String[] args) {

        System.out.println("""
                      ░██                           ░██                               \s
                      ░██                           ░██                               \s
                      ░██          ░███████   ░████████  ░████████  ░███████  ░██░████\s
                      ░██         ░██    ░██ ░██    ░██ ░██    ░██ ░██    ░██ ░███    \s
                      ░██         ░█████████ ░██    ░██ ░██    ░██ ░█████████ ░██     \s
                      ░██         ░██        ░██   ░███ ░██   ░███ ░██        ░██     \s
                      ░██████████  ░███████   ░█████░██  ░█████░██  ░███████  ░██     \s
                                                               ░██                    \s
                                                         ░███████                     \s
                
                """);
        loadTransactions(FILE_NAME);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D" -> addDeposit(scanner);
                case "P" -> addPayment(scanner);
                case "L" -> ledgerMenu(scanner);
                case "X" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
        scanner.close();
    }

    public static void loadTransactions(String fileName) {
        // TODO: create file if it does not exist, then read each line,
        //       parse the five fields, build a Transaction object,
        //       and add it to the transactions list.
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME)); //create a buffer reader.
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                LocalDate date = LocalDate.parse(tokens[0]);
                LocalTime time = LocalTime.parse(tokens[1]);
                String description = tokens[2];
                String vendor = tokens[3];
                double amount = Double.parseDouble(tokens[4]);
                transactions.add(new Transaction(date, time, description, vendor, amount)); // add the new transactions to the list.

            }
            reader.close(); // close the reader

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void addDeposit(Scanner scanner) {
        // TODO

        System.out.print("Enter date and time (yyyy-MM-dd HH:mm:ss): ");
        String dateTimeInput = scanner.nextLine();

        LocalDateTime dateTime = LocalDateTime.parse(dateTimeInput, DATETIME_FMT);

        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();

        System.out.print("Enter the description: ");
        String description = scanner.nextLine();

        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter the deposit amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (amount <= 0) {
            System.out.println("Amount must be positive(above 0): "); // print this message if the amount entered is not positive.
            return;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true)); // so its appends and not overwrite in the csv file.
            writer.write(date.format(DATE_FMT) + "|" +
                    time.format(TIME_FMT) + "|" +
                    description + "|" +
                    vendor + "|" +
                    amount);
            writer.newLine();

            transactions.add(new Transaction(date, time, description, vendor, amount));

            System.out.println("Deposit successfully added! ");
            writer.close(); // close the writer so it actually writes.

        } catch (Exception e) {
            System.err.println("Problem saving deposit: " + e.getMessage());
        }

    }

    private static void addPayment(Scanner scanner) {
        // TODO

        System.out.print("Enter date and time (yyyy-MM-dd HH:mm:ss): ");
        String dateTimeInput = scanner.nextLine();

        LocalDateTime dateTime = LocalDateTime.parse(dateTimeInput, DATETIME_FMT);  // change the users input into datetime object using the datetime formatter.

        LocalDate date = dateTime.toLocalDate(); // extracts the date
        LocalTime time = dateTime.toLocalTime(); // extracts the time

        System.out.print("Enter the description: ");
        String description = scanner.nextLine();

        System.out.print("Enter the vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter the deposit amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        if (amount <= 0) {
            System.out.println("Amount must be positive(above 0): "); // print this message if the amount entered is not positive.
            return;
        }
        amount = -amount; // after the input, change it to a negative.

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.write(date.format(DATE_FMT) + "|" +
                    time.format(TIME_FMT) + "|" +
                    description + "|" +
                    vendor + "|" +
                    amount);
            writer.newLine();
            transactions.add(new Transaction(date, time, description, vendor, amount));
            System.out.println("Payment was added successfully!");

            writer.close(); //close the buffered writer so it writes.

        } catch (Exception e) {
            System.out.println("Error registering the payment: " + e.getMessage());
            ;
        }

    }

    /* ------------------------------------------------------------------
       Ledger menu
       ------------------------------------------------------------------ */
    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A" -> displayLedger();
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "R" -> reportsMenu(scanner);
                case "H" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /* ------------------------------------------------------------------
       Display helpers: show data in neat columns
       ------------------------------------------------------------------ */
    private static void displayLedger() {
        /* TODO – print all transactions in column format, newest first */

        System.out.printf("%-12s | %-29s | %-23s | %12s%n", "DATE", "DESCRIPTION", "VENDOR", "AMOUNT");

        System.out.println("-------------|-------------------------------|-------------------------|-------------");

                for (int i = transactions.size() - 1; i >= 0; i--) { // get the last index, then go down the indexes until zero.
                    Transaction transaction = transactions.get(i);  // then assign it to transaction,so we can access its values.

                    System.out.printf("%-12s | %-29s | %-23s | $%11.2f%n",  // then call the getters to print it out

                            transaction.getDate(),
                            transaction.getDescription(),
                            transaction.getVendor(),
                            transaction.getAmount());
                }

        System.out.println("======================================================================================");

    }

    private static void displayDeposits() { /* TODO – only amount > 0               */ }

    private static void displayPayments() { /* TODO – only amount < 0               */ }

    /* ------------------------------------------------------------------
       Reports menu
       ------------------------------------------------------------------ */
    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> {/* TODO – month-to-date report */ }
                case "2" -> {/* TODO – previous month report */ }
                case "3" -> {/* TODO – year-to-date report   */ }
                case "4" -> {/* TODO – previous year report  */ }
                case "5" -> {/* TODO – prompt for vendor then report */ }
                case "6" -> customSearch(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /* ------------------------------------------------------------------
       Reporting helpers
       ------------------------------------------------------------------ */
    private static void filterTransactionsByDate(LocalDate start, LocalDate end) {
        // TODO – iterate transactions, print those within the range
    }

    private static void filterTransactionsByVendor(String vendor) {
        // TODO – iterate transactions, print those with matching vendor
    }

    private static void customSearch(Scanner scanner) {
        // TODO – prompt for any combination of date range, description,
        //        vendor, and exact amount, then display matches
    }

    /* ------------------------------------------------------------------
       Utility parsers (you can reuse in many places)
       ------------------------------------------------------------------ */
    private static LocalDate parseDate(String s) {
        /* TODO – return LocalDate or null */
        return null;
    }

    private static Double parseDouble(String s) {
        /* TODO – return Double   or null */
        return null;
    }
}
