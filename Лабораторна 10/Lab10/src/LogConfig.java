import java.io.IOException;
import java.util.logging.*;

public class LogConfig {
    public static void setup(Logger logger) {
        try {
            logger.setUseParentHandlers(false);

            FileHandler fileHandler = new FileHandler("lab_execution.log", true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            logger.addHandler(consoleHandler);

            logger.setLevel(Level.ALL);

        } catch (IOException e) {
            System.err.println("Не вдалося налаштувати логування: " + e.getMessage());
        }
    }
}