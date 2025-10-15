package org.example;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleRegexInput {
    private final Scanner scanner = new Scanner(System.in);

    public String readValidated(String prompt, String regex, String errorMessage) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (Pattern.matches(regex, input)) {
                return input;
            }
            System.out.println("⚠ " + errorMessage + " Спробуй ще раз.\n");
        }
    }
}
