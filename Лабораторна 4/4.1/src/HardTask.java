import java.util.ArrayList;
import java.util.List;

public class HardTask {

    public static void printAnimalNames(List<? extends Animal> animalList) {
        System.out.println("--- Початок друку списку ---");

        for (Animal animal : animalList) {
            // Можна безпечно ЧИТАТИ,
            // знаючи, що будь-який елемент є 'Animal'.
            System.out.println("Тварина: " + animal.getName());
        }

        //
        // ЗАБОРОНА (що "не треба робити"):
        // animalList.add(new Zebra("New Zebra")); // <-- ПОМИЛКА КОМПІЛЯЦІЇ
        //
        // Wildcard '? extends' (upper bound) забороняє 
        // додавати що-небудь у список,
        // захищаючи від додавання Зебри у список Левів.
        //

        System.out.println("--- Кінець списку ---");
    }

    public static void runDemo() {
        System.out.println("=== ЗАПУСК HARD TASK (Демонстрація Wildcard) ===");

        // Створюю ДВА РІЗНИХ списки:
        List<Lion> lions = new ArrayList<>();
        lions.add(new Lion("Сімба"));
        lions.add(new Lion("Муфаса"));

        List<Zebra> zebras = new ArrayList<>();
        zebras.add(new Zebra("Марті"));

        //
        // Завдяки wildcard '? extends Animal',
        // можна передати в метод ОБИДВА списки.
        //
        System.out.println("Передаємо список левів:");
        printAnimalNames(lions);

        System.out.println("\nПередаємо список зебр:");
        printAnimalNames(zebras);
    }
}