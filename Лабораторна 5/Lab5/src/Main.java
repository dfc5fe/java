import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final FileService fileService = new FileService();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = getUserChoice();

            // Обробка вибору в меню
            switch (choice) {
                case 1:
                    runTask1(); // Знайти рядок з макс. кількістю слів
                    break;
                case 2:
                    runTask2(); // Шифрування / Дешифрування
                    break;
                case 3:
                    runTask3(); // Підрахунок тегів з URL
                    break;
                case 0:
                    running = false;
                    System.out.println("Завершення програми.");
                    break;
                default:
                    System.out.println("Невірний вибір. Спробуй ще раз.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
        System.out.println("1. Завдання 1: Знайти рядок з макс. кількістю слів у файлі");
        System.out.println("2. Завдання 2: Шифрування / Дешифрування файлу");
        System.out.println("3. Завдання 3: Підрахувати теги на веб-сторінці");
        System.out.println("0. Вихід");
        System.out.print("Твій вибір: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Помилка: Введи число.");
            return -1;
        }
    }

    private static void runTask1() {
        System.out.print("Введи шлях до вхідного файлу (напр. 'input.txt'): ");
        String filePath = scanner.nextLine(); // Вимога 5

        String longestLine = "";
        int maxWords = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] words = currentLine.trim().split("\\s+");
                int currentWordCount = (words.length == 1 && words[0].isEmpty()) ? 0 : words.length;

                if (currentWordCount > maxWords) {
                    maxWords = currentWordCount;
                    longestLine = currentLine;
                }
            }
            System.out.println("Рядок з максимальною кількістю слів (" + maxWords + "):");
            System.out.println("'" + longestLine + "'");

        } catch (FileNotFoundException e) {
            System.err.println("Помилка: Файл не знайдено - " + filePath);
        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
    }

    private static void runTask2() {
        System.out.print("Обери режим (1 - Шифрувати, 2 - Дешифрувати): ");
        int mode = getUserChoice();

        System.out.print("Введи шлях до вхідного файлу: ");
        String inputFile = scanner.nextLine();
        System.out.print("Введи шлях до вихідного файлу: ");
        String outputFile = scanner.nextLine();
        System.out.print("Введи ключовий символ (напр. 'a'): ");
        char key = scanner.nextLine().charAt(0);

        if (mode == 1) {
            encryptFile(inputFile, outputFile, key);
        } else if (mode == 2) {
            decryptFile(inputFile, outputFile, key);
        } else {
            System.err.println("Невірний режим.");
        }
    }

    private static void encryptFile(String inputFile, String outputFile, char key) {
        try (Reader reader = new FileReader(inputFile);
             Writer writer = new FileWriter(outputFile);
             EncryptWriter encryptWriter = new EncryptWriter(writer, key)) {

            int c;
            while ((c = reader.read()) != -1) {
                encryptWriter.write(c);
            }
            System.out.println("Файл успішно зашифровано у " + outputFile);

        } catch (IOException e) {
            System.err.println("Помилка шифрування: " + e.getMessage());
        }
    }

    private static void decryptFile(String inputFile, String outputFile, char key) {
        try (Reader encryptedReader = new FileReader(inputFile);
             DecryptReader decryptReader = new DecryptReader(encryptedReader, key);
             Writer writer = new FileWriter(outputFile)) {

            int c;
            while ((c = decryptReader.read()) != -1) {
                writer.write(c);
            }
            System.out.println("Файл успішно дешифровано у " + outputFile);

        } catch (IOException e) {
            System.err.println("Помилка дешифрування: " + e.getMessage());
        }
    }

    private static void runTask3() {
        System.out.print("Введи URL для аналізу (напр. 'https://www.google.com'): ");
        String urlString = scanner.nextLine(); // Вимога 5

        Map<String, Integer> tagCounts = new TreeMap<>();

        Pattern tagPattern = Pattern.compile("<([a-zA-Z0-9]+)");

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0"); // Деякі сайти вимагають User-Agent

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Matcher matcher = tagPattern.matcher(line);
                    while (matcher.find()) {
                        String tag = matcher.group(1).toLowerCase();
                        tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
                    }
                }
            }

            displayAndSaveTagResults(tagCounts);

        } catch (MalformedURLException e) {
            System.err.println("Помилка: Невірний формат URL - " + urlString);
        } catch (IOException e) {
            System.err.println("Помилка підключення або читання з URL: " + e.getMessage());
        }
    }

    private static void displayAndSaveTagResults(Map<String, Integer> tagCounts) {
        if (tagCounts.isEmpty()) {
            System.out.println("Тегів не знайдено або сталася помилка.");
            return;
        }

        System.out.println("\n--- Результати: Теги в лексикографічному порядку ---");
        tagCounts.forEach((tag, count) ->
                System.out.println(tag + ": " + count)
        );

        System.out.println("\n--- Результати: Теги в порядку зростання частоти ---");

        Map<String, Integer> sortedByFrequency = tagCounts.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        sortedByFrequency.forEach((tag, count) ->
                System.out.println(tag + ": " + count)
        );

        System.out.println("\nЗберегти результати підрахунку тегів?");
        System.out.print("Введи ім'я файлу для збереження (або Enter, щоб пропустити): ");
        String savePath = scanner.nextLine();

        if (!savePath.trim().isEmpty()) {
            fileService.saveResults(tagCounts, savePath);
        }
    }
}