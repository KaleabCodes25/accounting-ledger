package com.pluralsight;

import java.io.*;
import java.nio.file.LinkOption;
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
            System.out.println("\n================================");
            System.out.println("             Ledger              ");
            System.out.println("=================================");

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
        System.out.println("\n==============================ALL TRANSACTIONS=======================================");

        System.out.printf("%-12s | %-29s | %-23s | %-12s%n", "DATE", "DESCRIPTION", "VENDOR", "AMOUNT");

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

    private static void displayDeposits() {
        //* TODO – only amount > 0

        System.out.println("\n==================================DEPOSITS==========================================");

        System.out.printf("%-12s | %-20s | %-20s | %12s%n", "DATE", "DESCRIPTION", "VENDOR", "AMOUNT");

        System.out.println("---------------------------------------------------------------------------------------");

        for (Transaction transaction : transactions) { // loop through the transactions list.

            if (transaction.getAmount() > 0) {  // if getAmount value is positive, then get those transactions.

                System.out.printf("%-12s | %-20s | %-20s | $%10.2f%n",
                        transaction.getDate(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());
            }

        }
        System.out.println("========================================================================================");
    }

    private static void displayPayments() {
        /* TODO – only amount < 0               */
        System.out.println("\n=====================================PAYMENTS========================================");

        System.out.printf("%-12s | %-25s | %-25s | %12s%n", "DATE", "DESCRIPTION", "VENDOR", "AMOUNT");

        System.out.println("---------------------------------------------------------------------------------------");

        for (Transaction transaction : transactions) { // loop through the transactions list.

            if (transaction.getAmount() < 0) {  // if getAmount value is negative, then get those transactions.

                System.out.printf("%-12s | %-30s | %-25s | $%10.2f%n",
                        transaction.getDate(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());

            }

        }

        System.out.println("=======================================================================================");
    }

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
                case "1" -> {
                    LocalDate today = LocalDate.now(); // get the local date.
                    LocalDate startOfMonth = today.withDayOfMonth(1);
                    filterTransactionsByDate(startOfMonth,today);
                }
                case "2" -> {
                    LocalDate month = LocalDate.now();
                    LocalDate previousMonth = month.minusMonths(1); //use minusMonths method to get the previous month.

                    LocalDate start = previousMonth.withDayOfMonth(1);
                    LocalDate end = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()); // we want the local date, so this gives us the end of the previous month.
                    filterTransactionsByDate(start,end);

                }
                case "3" -> {
                    LocalDate now = LocalDate.now();
                    LocalDate yearBegin = now.withDayOfYear(1);  // use this method to get the first day of the year.
                    filterTransactionsByDate(yearBegin,now);
                }
                case "4" -> {
                    LocalDate now = LocalDate.now();
                    LocalDate previousYear = now.minusYears(1);  // use minusYears method to get the previous year.

                    LocalDate start = previousYear.withDayOfYear(1);
                    LocalDate end = previousYear.withDayOfYear(previousYear.lengthOfYear());
                    filterTransactionsByDate(start,end);

                     }
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

        for (Transaction transaction : transactions) { // loop through transactions.
            LocalDate date = transaction.getDate(); // get the date of each transaction.

            if(!date.isBefore(start) && !date.isAfter(end)){ // then check if the date is before(start) and after(start), ! makes it so the parameters are included
                System.out.printf("%-12s | %-30s | %-25s | $%10.2f%n", // then print every transaction that fits the range.
                        transaction.getDate(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount());

            }
        }


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
        LocalDate today = LocalDate.now();

        return null;
    }

    private static Double parseDouble(String s) {
        /* TODO – return Double   or null */
        return null;
    }

}
