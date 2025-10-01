package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static void printLine() {
        System.out.println("-".repeat(40));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String continueInput;
        CuratorJournal journal = new CuratorJournal();

        do {
            System.out.println("\tВведи дані студента");

            String lastName = ConsoleRegexInput.getInput(scanner, "Прізвище студента", "^[А-Яа-яЇїІіЄєҐґA-Za-z\\-]+$", "Прізвище повинно містити лише літери та дефіс.");
            String firstName = ConsoleRegexInput.getInput(scanner, "Ім'я студента", "^[А-Яа-яЇїІіЄєҐґA-Za-z\\-]+$", "Ім'я повинно містити лише літери та дефіс.");
            String birthDate = getValidatedDate(scanner, "Дата народження (дд/мм/рррр)");
            String phoneNumber = ConsoleRegexInput.getInput(scanner, "Телефон (формат +380XXXXXXXXX)", "^\\+380\\d{9}$", "Номер телефону має починатися з +380 та містити 9 цифр.");
            String address = ConsoleRegexInput.getInput(scanner, "Адреса (вулиця будинок квартира)", "^.+\\s+\\d+\\s+\\d+$", "Адреса має містити вулицю, номер будинку та квартиру.");

            JournalEntry record = new JournalEntry(lastName, firstName, birthDate, phoneNumber, address);
            journal.addJournalEntry(record);

            System.out.println("Запис успішно додано у журнал.");

            List<JournalEntry> records = journal.getAllEntries();
            System.out.println("\nУсі записи журналу:");
            for (JournalEntry entry : records) {
                System.out.println(entry);
            }

            System.out.print("\nБажаєш додати новий запис? (т/н): ");
            continueInput = scanner.nextLine().trim().toLowerCase();

            printLine();
        } while (continueInput.equals("т") || continueInput.equals("так") || continueInput.equals("y") || continueInput.equals("yes"));

        scanner.close();

        if (!continueInput.equals("н") && !continueInput.equals("ні") && !continueInput.equals("no")) {
            System.out.println("Програма завершить роботу (некоректна відповідь).");
        }
    }

    private static String getValidatedDate(Scanner scanner, String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print(prompt + ": ");
            String input = scanner.nextLine().trim();
            try {
                LocalDate.parse(input, formatter);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Помилка: дата повинна бути у форматі дд/мм/рррр.");
            }
        }
    }
}
