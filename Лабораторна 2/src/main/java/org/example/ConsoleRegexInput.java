package org.example;

import java.util.regex.Pattern;
import java.util.Scanner;

public class ConsoleRegexInput {
    public static String getInput(Scanner scanner, String prompt, String regex, String errorMessage) {
        String input;
        Pattern pattern = Pattern.compile(regex);

        while (true) {
            System.out.print(prompt + ": ");
            input = scanner.nextLine().trim();
            if (pattern.matcher(input).matches()) {
                return input;
            } else {
                System.out.println("Помилка: " + errorMessage);
            }
        }
    }
}
