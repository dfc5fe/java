import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=================================");
            System.out.println("Оберіть завдання для запуску:");
            System.out.println("1. Task 1 (Bank / Deadlocks)");
            System.out.println("2. Task 2 (Ring Buffer / Producer-Consumer)");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    BankSystem.runTask();
                    break;
                case "2":
                    RingBufferSystem.runTask();
                    break;
                case "0":
                    System.out.println("Вихід з програми...");
                    return;
                default:
                    System.out.println("Невірний вибір. Спробуйте ще раз.");
            }

            // Невелика пауза, щоб консоль не змішувалася
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
    }
}