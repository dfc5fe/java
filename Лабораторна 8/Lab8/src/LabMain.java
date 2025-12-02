import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class LabMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n----------------------------------");
            System.out.println("Обери завдання для запуску:");
            System.out.println("1. Simple Task (Monte Carlo Pi)");
            System.out.println("2. Middle Task (ForkJoin Sum)");
            System.out.println("0. Вихід");
            System.out.print("Твій вибір: ");

            String choice = scanner.next();

            switch (choice) {
                case "1":
                    System.out.print("Введи кількість потоків (наприклад, 4, 8, 12): ");
                    int threads = scanner.nextInt();
                    ParallelMonteCarloPi.run(threads);
                    break;

                case "2":
                    runForkJoinTask();
                    break;

                case "0":
                    System.out.println("Пака!");
                    return;

                default:
                    System.out.println("Невірний вибір. Спробуй ще раз.");
            }
        }
    }

    private static void runForkJoinTask() {
        System.out.println("\n=== ГЕНЕРАЦІЯ МАСИВУ... ===");
        int size = 1_000_000;
        int[] array = new int[size];
        Random random = new Random();

        // Заповнюємо масив
        long expectedSum = 0;
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(101); // 0..100
            expectedSum += array[i];
        }
        System.out.println("Масив на " + size + " елементів згенеровано.");

        // ForkJoin
        ForkJoinPool pool = new ForkJoinPool();
        ArraySumTask task = new ArraySumTask(array, 0, size);

        long startTime = System.currentTimeMillis();
        long result = pool.invoke(task);
        long endTime = System.currentTimeMillis();

        System.out.println("\n=== РЕЗУЛЬТАТ FORK/JOIN ===");
        System.out.println("Сума (ForkJoin): " + result);
        System.out.println("Сума (Control):  " + expectedSum + " (для перевірки)");
        System.out.println("Час виконання:   " + (endTime - startTime) + "ms");

        if (result == expectedSum) {
            System.out.println("✅ Результати співпадають!");
        } else {
            System.out.println("❌ Помилка в обчисленнях!");
        }
    }
}