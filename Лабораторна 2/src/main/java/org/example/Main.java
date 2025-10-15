package org.example;

public class Main {
    public static void main(String[] args) {
        CuratorJournal journal = new CuratorJournal();
        ConsoleRegexInput input = new ConsoleRegexInput();

        System.out.println("Журнал куратора\n");

        while (true) {
            String lastName = input.readValidated(
                    "Введи прізвище студента: ",
                    "^[А-Яа-яЇїІіЄєҐґ'\\-]{2,}$",
                    "Використовуй лише українські літери."
            );

            String firstName = input.readValidated(
                    "Введи ім'я студента: ",
                    "^[А-Яа-яЇїІіЄєҐґ'\\-]{2,}$",
                    "Використовуй лише українські літери."
            );

            String birthDate = input.readValidated(
                    "Введи дату народження (у форматі ДД.ММ.РРРР): ",
                    "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.(19|20)\\d{2}$",
                    "Неправильний формат дати. Приклад: 25.12.2000"
            );

            String phoneNumber = input.readValidated(
                    "Введи телефон (у форматі +380XXXXXXXXX): ",
                    "^\\+380\\d{9}$",
                    "Номер телефону повинен бути у форматі +380XXXXXXXXX."
            );

            String address = input.readValidated(
                    "Введи адресу (вулиця, будинок, квартира): ",
                    "^[А-Яа-яЇїІіЄєҐґ0-9\\s,.-]{5,}$",
                    "Адреса повинна містити лише українські літери, цифри та символи ,.-"
            );

            JournalEntry entry = new JournalEntry(lastName, firstName, birthDate, phoneNumber, address);
            journal.addEntry(entry);

            String continueChoice = input.readValidated(
                    "Бажаєш додати ще один запис? (так/ні): ",
                    "^(так|ні)$",
                    "Введи лише 'так' або 'ні'."
            );

            if (continueChoice.equalsIgnoreCase("ні")) {
                break;
            }
        }

        journal.showAllEntries();
        System.out.println("Роботу завершено.");
    }
}
