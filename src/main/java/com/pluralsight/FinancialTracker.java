package com.pluralsight;


import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FinancialTracker {
    private static final String RESET = "\u001B[0m";  //
    private static final String BOLD = "\u001B[1m";  // ANSI Colors

    private static final String GOLD = "\u001B[38;2;212;175;55m";
    private static final String YELLOW = "\u001B[33m";

    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String BRIGHT_BLUE = "\u001B[94m";

    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern(TIME_PATTERN);
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    public static void main(String[] args) {
        System.out.println(GOLD + BOLD);

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
        System.out.println(RESET);
        loadTransactions(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println(BOLD + "Welcome to TransactionApp");
            System.out.println(BOLD + "Choose an option:");
            System.out.println(GOLD + BOLD + "===== LEDGER =====" + RESET);
            System.out.println(BOLD + GREEN + "D) Add Deposit " + RESET);
            System.out.println(BOLD + RED + "P) Make Payment (Debit)" + RESET);
            System.out.println(BOLD + GOLD + "L) Ledger " + RESET);
            System.out.println(BOLD + "X) Exit" + RESET);

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

        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                LocalDate date = LocalDate.parse(tokens[0]);
                LocalTime time = LocalTime.parse(tokens[1]);
                String description = tokens[2];
                String vendor = tokens[3];
                double amount = Double.parseDouble(tokens[4]);
                transactions.add(new Transaction(date, time, description, vendor, amount));

            }
            reader.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void addDeposit(Scanner scanner) {
        String dateTimeInput = null;
        try {
            System.out.print("Enter date and time (yyyy-MM-dd HH:mm:ss): ");
            dateTimeInput = scanner.nextLine();
            LocalDateTime.parse(dateTimeInput, DATETIME_FMT);
        } catch (Exception e) {
            System.err.println("Enter a valid Date in the required format : " + e.getMessage());
            return;
        }

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
            System.out.println("Amount must be positive(above 0): ");
            return;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.write(date.format(DATE_FMT) + "|" + time.format(TIME_FMT) + "|" + description + "|" + vendor + "|" + amount);
            writer.newLine();

            transactions.add(new Transaction(date, time, description, vendor, amount));

            System.out.println("Deposit successfully added! ");
            writer.close();

        } catch (Exception e) {
            System.err.println("Problem saving deposit: " + e.getMessage());
        }

    }

    private static void addPayment(Scanner scanner) {

        String dateTimeInput = null;
        try {
            System.out.print("Enter date and time (yyyy-MM-dd HH:mm:ss): ");
            dateTimeInput = scanner.nextLine();
            LocalDateTime.parse(dateTimeInput, DATETIME_FMT);
        } catch (Exception e) {
            System.err.println("Enter the date in required format: " + e.getMessage());
            return;
        }

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
            System.out.println("Amount must be positive(above 0): ");
            return;
        }
        amount = -amount;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.write(date.format(DATE_FMT) + "|" + time.format(TIME_FMT) + "|" + description + "|" + vendor + "|" + amount);
            writer.newLine();

            transactions.add(new Transaction(date, time, description, vendor, amount));
            System.out.println("Payment was added successfully!");

            writer.close();

        } catch (Exception e) {
            System.out.println("Error registering the payment: " + e.getMessage());
            ;
        }

    }
    private static void ledgerMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("==============================");
            System.out.println(GOLD + "         LEDGER            " + RESET);
            System.out.println("==============================");

            System.out.println("Choose an option:");
            System.out.println(YELLOW + BOLD + "A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home" + RESET);

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
    private static void displayLedger() {
        System.out.println(GOLD + "==============================ALL TRANSACTIONS=======================================" + RESET);

        System.out.printf("%-12s | %-29s | %-23s | %-12s%n", "DATE", "DESCRIPTION", "VENDOR", "AMOUNT");

        System.out.println("-------------|-------------------------------|-------------------------|-------------");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);

            String color;
            if (transaction.getAmount() < 0) {
                color = RED;
            } else {
                color = GREEN;
            }

            System.out.printf(color + "%-12s | %-29s | %-23s | $%11.2f%n" + RESET,  // then call the getters to print it out

                    transaction.getDate(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount());

        }
        System.out.println("======================================================================================");

    }
    private static void displayDeposits() {


        System.out.println(GOLD + "==================================DEPOSITS==========================================" + RESET);

        System.out.printf(GOLD + "%-12s | %-20s | %-20s | %12s%n", "DATE", "DESCRIPTION", "VENDOR", "AMOUNT" + RESET);

        System.out.println("---------------------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {

            if (transaction.getAmount() > 0) {

                System.out.printf(GREEN + "%-12s | %-20s | %-20s | $%10.2f%n" + RESET, transaction.getDate(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }
        System.out.println("========================================================================================");
    }

    private static void displayPayments() {
        System.out.println(GOLD + "\n=====================================PAYMENTS========================================" + RESET);

        System.out.printf(GOLD + "%-12s | %-25s | %-25s | %12s%n", "DATE", "DESCRIPTION", "VENDOR", "AMOUNT" + RESET);

        System.out.println("---------------------------------------------------------------------------------------");

        for (Transaction transaction : transactions) { // loop through the transactions list.

            if (transaction.getAmount() < 0) {  // if getAmount value is negative, then get those transactions.

                System.out.printf(RED + "%-12s | %-30s | %-25s | $%10.2f%n" + RESET, transaction.getDate(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());

            }
        }
        System.out.println("=======================================================================================");
    }

    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println(BOLD + "Reports");
            System.out.println(BRIGHT_BLUE + BOLD + "Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back" + RESET);

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> {
                    LocalDate today = LocalDate.now(); // get the local date.
                    LocalDate startOfMonth = today.withDayOfMonth(1);
                    System.out.println(GOLD + "=================================START OF MONTH====================================" + RESET);
                    filterTransactionsByDate(startOfMonth, today);
                }
                case "2" -> {
                    LocalDate month = LocalDate.now();
                    LocalDate previousMonth = month.minusMonths(1);

                    LocalDate start = previousMonth.withDayOfMonth(1);
                    LocalDate end = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth());
                    System.out.println(GOLD + "==================================PREVIOUS MONTH=====================================" + RESET);
                    filterTransactionsByDate(start, end);

                }
                case "3" -> {
                    LocalDate now = LocalDate.now();
                    LocalDate yearBegin = now.withDayOfYear(1);  // use this method to get the first day of the year.
                    System.out.println(GOLD + "=================================BEGINNING OF YEAR====================================" + RESET);
                    filterTransactionsByDate(yearBegin, now);
                }
                case "4" -> {
                    LocalDate now = LocalDate.now();
                    LocalDate previousYear = now.minusYears(1);

                    LocalDate start = previousYear.withDayOfYear(1);
                    LocalDate end = previousYear.withDayOfYear(previousYear.lengthOfYear());
                    System.out.println(GOLD + "===================================PREVIOUS YEAR======================================" + RESET);
                    filterTransactionsByDate(start, end);

                }
                case "5" -> {
                    System.out.println("Enter the name of the vendor: ");
                    String vendor = scanner.nextLine().trim();
                    filterTransactionsByVendor(vendor);
                }
                case "6" -> customSearch(scanner);

                case "0" -> running = false;

                default -> System.out.println("Invalid option");
            }

        }
    }

    private static void filterTransactionsByDate(LocalDate start, LocalDate end) {

        for (Transaction transaction : transactions) {
            LocalDate date = transaction.getDate();

            if (!date.isBefore(start) && !date.isAfter(end)) {
                System.out.printf("%-12s | %-30s | %-25s | $%10.2f%n", transaction.getDate(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());

            }
        }
    }
    private static void filterTransactionsByVendor(String vendor) {
        System.out.println(GOLD + "=====================================VENDOR========================================" + RESET);
        boolean found = false;
        for (Transaction transaction : transactions) {
            if (vendor.equalsIgnoreCase(transaction.getVendor())) {
                System.out.printf("%-12s | %-30s | %-25s | $%10.2f%n", transaction.getDate(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
                found = true;
            }
        }
        if (!found) {
            System.out.println("NO transactions that match your search criteria!!"); // if it doesn't match, then print this out.
        }

    }

    private static void customSearch(Scanner scanner) {
        System.out.println(GOLD + "===================================CUSTOM SEARCH======================================" + RESET);
        String startDate = null;
        String endDate = null;
        try {
            System.out.print("Enter a start date(yyyy-MM-dd) or skip by pressing Enter: ");
            startDate = scanner.nextLine().trim();
            parseDate(startDate);

            System.out.print("Enter an end date(yyyy-MM-dd) or skip by pressing Enter: ");
            endDate = scanner.nextLine().trim();
            parseDate(endDate);
        } catch (Exception e) {
            System.err.println("Enter a valid date yyyy-MM-dd: " + e.getMessage());
            return;
        }

        System.out.print("Enter a description or skip by pressing Enter: ");
        String description = scanner.nextLine().trim();

        System.out.print("Enter a vendor name or skip by pressing Enter: ");
        String vendor = scanner.nextLine().trim();

        System.out.println("Enter the amount you want to search or skip by pressing Enter: ");
        String amount = scanner.nextLine().trim();

        for (Transaction transaction : transactions) {

            boolean foundMatch = true;

            if (!startDate.isEmpty() && transaction.getDate().isBefore(parseDate(startDate))) {

                foundMatch = false;
            }
            if (!endDate.isEmpty() && transaction.getDate().isAfter(parseDate(endDate))) {
                foundMatch = false;

            }
            if (!description.isEmpty() && !transaction.getDescription().toLowerCase().contains(description.toLowerCase())) {
                foundMatch = false;

            }
            if (!vendor.isEmpty() && !transaction.getVendor().equalsIgnoreCase(vendor)) {
                foundMatch = false;

            }
            if (!amount.isEmpty() && (Double.compare(transaction.getAmount(), parseDouble(amount)) != 0)) {
                foundMatch = false;

            }
            if (foundMatch) {
                System.out.printf("%-12s | %-30s | %-25s | $%10.2f%n", transaction.getDate(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }

    }

    private static LocalDate parseDate(String s) {
        if (s.isEmpty()) {
            return null;
        }
        return LocalDate.parse(s);
    }

    private static Double parseDouble(String s) {
        if (s.isEmpty()) {
            return null;
        }
        return Double.parseDouble(s);
    }
    /*private static LocalDateTime parseDateTime(String s)
        if (s.isEmpty()) { // checking to see its empty.
            return null;
        }
        return LocalDateTime.parse(s);*/





}
