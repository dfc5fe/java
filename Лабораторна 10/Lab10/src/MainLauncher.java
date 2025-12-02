import java.util.Scanner;

public class MainLauncher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("да, я оптиміст, визнаю");
        System.out.println("1. Завдання 1: Рефлексія (Зміна String)");
        System.out.println("2. Завдання 2-3: Система логування та I18n (Модифікована Лаба 5)");
        System.out.println("0. Вихід");
        System.out.print("Твій вибір: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                ReflectionTask.run();
                break;
            case "2":
                Lab5Main.run();
                break;
            case "0":
                System.out.println("пака!");
                break;
            default:
                System.out.println("Невірний вибір.");
        }
    }
}