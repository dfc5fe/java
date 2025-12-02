import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lab5Main {
    private static final Logger logger = Logger.getLogger(Lab5Main.class.getName());
    private static ResourceBundle bundle;
    private static Scanner scanner;
    private static final FileService fileService = new FileService();

    public static void run() {
        scanner = new Scanner(System.in);
        LogConfig.setup(logger);
        logger.info("Lab5 module started");

        setupLanguage();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    logger.info("User selected Task 1");
                    runTask1();
                    break;
                case 2:
                    logger.info("User selected Task 2");
                    runTask2();
                    break;
                case 3:
                    logger.info("User selected Task 3");
                    runTask3();
                    break;
                case 0:
                    running = false;
                    System.out.println(bundle.getString("log.end"));
                    logger.info("Lab5 module finished");
                    break;
                default:
                    System.out.println(bundle.getString("error.number"));
                    logger.warning("Invalid menu selection: " + choice);
            }
        }
    }

    private static void setupLanguage() {
        System.out.println("1. Українська");
        System.out.println("2. English");
        System.out.print("Обери мову / Select language: ");

        String langChoice = scanner.nextLine();
        Locale locale;
        if (langChoice.equals("2")) {
            locale = new Locale("en", "US");
        } else {
            locale = new Locale("uk", "UA");
        }

        try {
            bundle = ResourceBundle.getBundle("resources.location.messages", locale);
            logger.info("Language set to: " + locale);
        } catch (MissingResourceException e) {
            System.err.println("Resource file not found, defaulting to manual strings.");
            logger.severe("Could not find resource bundle!");
            // Фолбек, шоб не крашилось
            System.exit(1);
        }
    }

    private static void printMenu() {
        System.out.println("\n" + bundle.getString("menu.main"));
        System.out.println(bundle.getString("menu.task1"));
        System.out.println(bundle.getString("menu.task2"));
        System.out.println(bundle.getString("menu.task3"));
        System.out.println(bundle.getString("menu.exit"));
        System.out.print(bundle.getString("prompt.choice"));
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            logger.warning("User entered non-integer input");
            return -1;
        }
    }

    private static void runTask1() {
        System.out.print(bundle.getString("prompt.file.input"));
        String filePath = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String longestLine = "";
            int maxWords = 0;
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] words = currentLine.trim().split("\\s+");
                int currentWordCount = (words.length == 1 && words[0].isEmpty()) ? 0 : words.length;
                if (currentWordCount > maxWords) {
                    maxWords = currentWordCount;
                    longestLine = currentLine;
                }
            }
            System.out.println(bundle.getString("msg.success"));
            System.out.println("Result: " + longestLine);
            logger.info("Task 1 completed successfully. Max words: " + maxWords);

        } catch (IOException e) {
            System.err.println(bundle.getString("error.file") + e.getMessage());
            logger.log(Level.SEVERE, "Task 1 Error", e);
        }
    }

    private static void runTask2() {
        System.out.print(bundle.getString("msg.encrypt.mode"));
        int mode = getUserChoice();

        System.out.print(bundle.getString("prompt.file.input"));
        String inputFile = scanner.nextLine();
        System.out.print(bundle.getString("prompt.file.output"));
        String outputFile = scanner.nextLine();
        System.out.print(bundle.getString("prompt.key"));
        String keyStr = scanner.nextLine();

        if(keyStr.isEmpty()) {
            logger.warning("Empty key provided");
            return;
        }
        char key = keyStr.charAt(0);

        try {
            if (mode == 1) {
                try (Reader reader = new FileReader(inputFile);
                     Writer writer = new FileWriter(outputFile);
                     EncryptWriter encryptWriter = new EncryptWriter(writer, key)) { // Використовуємо твій клас
                    int c;
                    while ((c = reader.read()) != -1) encryptWriter.write(c);
                }
                logger.info("File encrypted: " + inputFile + " -> " + outputFile);
            } else if (mode == 2) {
                try (Reader reader = new FileReader(inputFile);
                     DecryptReader decryptReader = new DecryptReader(reader, key); // Використовуємо твій клас
                     Writer writer = new FileWriter(outputFile)) {
                    int c;
                    while ((c = decryptReader.read()) != -1) writer.write(c);
                }
                logger.info("File decrypted: " + inputFile + " -> " + outputFile);
            }
            System.out.println(bundle.getString("msg.success"));
        } catch (IOException e) {
            System.err.println(bundle.getString("error.file") + e.getMessage());
            logger.log(Level.SEVERE, "Encryption/Decryption error", e);
        }
    }

    // --- Task 3 Logic ---
    private static void runTask3() {
        System.out.print(bundle.getString("prompt.url"));
        String urlString = scanner.nextLine();
        Map<String, Integer> tagCounts = new TreeMap<>();
        Pattern tagPattern = Pattern.compile("<([a-zA-Z0-9]+)");

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

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

            System.out.println(bundle.getString("msg.success"));
            tagCounts.forEach((k, v) -> System.out.println(k + ": " + v));
            logger.info("Task 3 completed. Unique tags found: " + tagCounts.size());

            System.out.print(bundle.getString("prompt.save"));
            String savePath = scanner.nextLine();
            if (!savePath.trim().isEmpty()) {
                fileService.saveResults(tagCounts, savePath);
            }

        } catch (Exception e) {
            System.err.println(bundle.getString("error.url") + e.getMessage());
            logger.log(Level.SEVERE, "URL Analysis Error", e);
        }
    }
}