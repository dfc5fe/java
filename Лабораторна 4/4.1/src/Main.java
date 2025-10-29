import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Обери завдання для запуску:");
        System.out.println("1: Завдання 4.2 (Вольєри та Тварини)");
        System.out.println("2: Hard Task (Демонстрація Wildcard)");
        System.out.print("Твій вибір (1 або 2): ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Помилка. Введи 1 або 2.");
            scanner.close();
            return;
        }

        System.out.println("------------------------------------");

        switch (choice) {
            case 1:
                runTask42Demo();
                break;
            case 2:
                // Викликаємо статичний метод з класу HardTask
                HardTask.runDemo();
                break;
            default:
                System.out.println("Невідомий вибір. Завершення роботи.");
        }

        scanner.close();
    }

    public static void runTask42Demo() {
        System.out.println("=== ЗАПУСК ЗАВДАННЯ 4.2 (Вольєри та Тварини) ===");

        System.out.println("--- Створення вольєрів ---");
        LionCage lionCage = new LionCage(2);
        UngulateCage ungulateCage = new UngulateCage(4);
        GeneralBirdCage birdCage = new GeneralBirdCage(3);

        System.out.println("\n--- ТЕСТ 1: Наповнення вольєрів ---");
        lionCage.addAnimal(new Lion("Сімба"));
        ungulateCage.addAnimal(new Zebra("Марті"));
        ungulateCage.addAnimal(new Giraffe("Мелман"));
        birdCage.addAnimal(new Eagle("Скай"));

        System.out.println("\n--- ТЕСТ 2: Перевірка обмежень Generics ---");
        System.out.println("Помилки компіляції (закоментовані в коді) доводять обмеження.");

        System.out.println("\n--- ТЕСТ 3: Тестування винятку EnclosureFullException ---");
        try {
            lionCage.addAnimal(new Lion("Муфаса"));
            lionCage.addAnimal(new Lion("Шрам")); // Переповнення
        } catch (EnclosureFullException e) {
            System.out.println("ПІЙМАНО ВИПАДОК: " + e.getMessage());
        }
        System.out.println("Зайнято місць у вольєрі для левів: " + lionCage.getOccupiedPlaces());

        System.out.println("\n--- ТЕСТ 4: Тестування винятку AnimalNotFoundException ---");
        try {
            Lion scar = new Lion("Шрам");
            lionCage.removeAnimal(scar); // Такого не додав
        } catch (AnimalNotFoundException e) {
            System.out.println("ПІЙМАНО ВИПАДОK: " + e.getMessage());
        }

        System.out.println("\n--- ТЕСТ 5: Тестування класу Zoo та Wildcards ---");
        Zoo myZoo = new Zoo();
        myZoo.addCage(lionCage);
        myZoo.addCage(ungulateCage);
        myZoo.addCage(birdCage);

        System.out.println("------------------------------------");
        System.out.println("Всього тварин у зоопарку: " + myZoo.getCountOfAnimals());
        System.out.println("------------------------------------");
    }
}