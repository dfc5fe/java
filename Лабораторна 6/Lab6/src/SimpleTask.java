import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Клас, що відповідає за логіку перекладу
class Translator {
    private Map<String, String> dictionary;

    public Translator() {
        dictionary = new HashMap<>();
        initDictionary();
    }

    // Початкове наповнення словника
    private void initDictionary() {
        dictionary.put("hello", "привіт");
        dictionary.put("world", "світ");
        dictionary.put("java", "джава");
        dictionary.put("task", "завдання");
        dictionary.put("code", "код");
        dictionary.put("apple", "яблуко");
        dictionary.put("computer", "комп'ютер");
    }

    // Метод додавання нового слова
    public void addWord(String english, String ukrainian) {
        dictionary.put(english.toLowerCase().trim(), ukrainian.toLowerCase().trim());
        System.out.println(">> Успішно додано: [" + english + " - " + ukrainian + "]");
    }

    // Метод перекладу фрази
    public String translatePhrase(String phrase) {
        String[] words = phrase.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            // Очищуємо слово від знаків пунктуації для пошуку
            String cleanWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();

            // Якщо слово є в мапі — беремо переклад, якщо ні — залишаємо оригінал
            String translatedWord = dictionary.getOrDefault(cleanWord, word);

            result.append(translatedWord).append(" ");
        }
        return result.toString().trim();
    }
}

// Головний клас для запуску
public class SimpleTask {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Translator translator = new Translator();
        String choice;

        System.out.println("=== SIMPLE TASK: TRANSLATOR ===");

        do {
            System.out.println("\n1. Додати слово до словника");
            System.out.println("2. Перекласти фразу");
            System.out.println("0. Вихід");
            System.out.print("Твій вибір: ");

            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введи слово англійською: ");
                    String eng = scanner.nextLine();
                    System.out.print("Введи переклад українською: ");
                    String ukr = scanner.nextLine();
                    if (!eng.isEmpty() && !ukr.isEmpty()) {
                        translator.addWord(eng, ukr);
                    } else {
                        System.out.println("Помилка: поля не можуть бути порожніми.");
                    }
                    break;
                case "2":
                    System.out.print("Введи фразу англійською: ");
                    String phrase = scanner.nextLine();
                    String translation = translator.translatePhrase(phrase);
                    System.out.println("---------------------------");
                    System.out.println("Переклад: " + translation);
                    System.out.println("---------------------------");
                    break;
                case "0":
                    System.out.println("Роботу завершено.");
                    break;
                default:
                    System.out.println("Невірний варіант, спробуй ще раз.");
            }
        } while (!choice.equals("0"));

        scanner.close();
    }
}