import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LabTasks tasks = new LabTasks();

        while (true) {
            System.out.println("\n================ МЕНЮ ================");
            System.out.println("1. Запустити Task 1.1 та 1.2 (Рядки та Факторіал)");
            System.out.println("2. Запустити Task 2.1 (Domain Model - Розробники)");
            System.out.println("3. Запустити Task 3.1 (Асинхронний пошук досконалих чисел)");
            System.out.println("0. Вихід");
            System.out.print("Вибери опцію: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        runTask1(tasks);
                        break;
                    case "2":
                        runTask2(tasks);
                        break;
                    case "3":
                        runTask3(tasks, scanner);
                        break;
                    case "0":
                        System.out.println("пака");
                        return;
                    default:
                        System.out.println("Невірний вибір, спробуй ще раз.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void runTask1(LabTasks tasks) {
        System.out.println("\n--- Task 1.1: Акронім ---");
        List<String> words = Arrays.asList("Hello", "World", "Java", "Stream");
        System.out.println("Вхідні слова: " + words);
        String acronym = tasks.getFirstLettersString(words);
        System.out.println("Результат (перші літери): " + acronym);

        System.out.println("\n--- Task 1.2: Факторіал ---");
        int num = 6;
        long factorial = tasks.calculateFactorial(num);
        System.out.println("Факторіал числа " + num + " = " + factorial);
    }

    private static void runTask2(LabTasks tasks) {
        System.out.println("\n--- Task 2.1: Developer Service ---");

        Developer dev1 = new Developer("Олексій", Arrays.asList("Java", "SQL", "HTML"));
        Developer dev2 = new Developer("Марія", Arrays.asList("Python", "Java", "Docker"));
        Developer dev3 = new Developer("Дмитро", Arrays.asList("C++", "Python"));

        List<Developer> team = Arrays.asList(dev1, dev2, dev3);

        System.out.println("Команда:");
        team.forEach(d -> System.out.println(" - " + d));

        List<String> allLangs = tasks.getAllUniqueLanguages(team);
        System.out.println("Всі унікальні мови команди: " + allLangs);
    }

    private static void runTask3(LabTasks tasks, Scanner scanner) throws ExecutionException, InterruptedException {
        System.out.println("\n--- Task 3.1: Perfect Numbers (Async) ---");
        System.out.print("Введи максимальне число для пошуку (напр. 10000): ");

        int limit;
        try {
            limit = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Помилка: введи ціле число.");
            return;
        }

        long startTime = System.currentTimeMillis();

        // Запускаємо асинхронно
        var future = tasks.findPerfectNumbersAsync(limit);

        System.out.println(">> Основний потік не заблокований. Можемо робити щось інше...");

        // get блокує потік до завершення обчислень
        List<Integer> result = future.get();

        long endTime = System.currentTimeMillis();

        System.out.println("Знайдені досконалі числа: " + result);
        System.out.println("Час виконання: " + (endTime - startTime) + " мс");
    }
}