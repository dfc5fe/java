import java.lang.reflect.Field;
import java.util.Scanner;

public class ReflectionTask {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- ЗАВДАННЯ 1: РЕФЛЕКСІЯ (Злом String) ---");

        // 1. Створення рядка літералом (String Pool)
        String literalString = "Java";
        System.out.println("1. Рядок-літерал ДО змін: " + literalString);
        System.out.println("   (Увага: зміна літералу вплине на всі такі ж рядки в програмі!)");

        // 2. Створення рядка через new (Heap)
        System.out.print("Введи рядок для експерименту (через new): ");
        String inputString = new String(scanner.nextLine());
        System.out.println("2. Введений рядок ДО змін: " + inputString);

        System.out.print("Введи текст, на який хочещ замінити внутрішнє значення: ");
        String replacement = scanner.nextLine();

        try {
            System.out.println("\n...Зламуємо рядки через Reflection...");
            modifyStringInternal(literalString, replacement);
            modifyStringInternal(inputString, replacement);

            System.out.println("\nРЕЗУЛЬТАТИ:");
            System.out.println("1. Рядок-літерал ПІСЛЯ змін: " + literalString);
            System.out.println("   Перевірка іншої змінної з текстом \"Java\": " + "Java");
            System.out.println("2. Введений рядок ПІСЛЯ змін: " + inputString);

        } catch (Exception e) {
            System.err.println("Помилка рефлексії: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void modifyStringInternal(String target, String newValue) throws Exception {
        Field valueField = String.class.getDeclaredField("value");
        valueField.setAccessible(true);

        Object internalArray = valueField.get(target);

        if (internalArray instanceof char[]) {
            char[] newChars = newValue.toCharArray();
            valueField.set(target, newChars);
        } else if (internalArray instanceof byte[]) {
            byte[] newBytes = newValue.getBytes();
            valueField.set(target, newBytes);

            try {
                Field hashField = String.class.getDeclaredField("hash");
                hashField.setAccessible(true);
                hashField.setInt(target, 0);
            } catch (NoSuchFieldException ignored) {}
        }
    }
}