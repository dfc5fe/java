import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileService {
    private static final Logger logger = Logger.getLogger(FileService.class.getName());

    public void saveResults(Object data, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data);
            logger.log(Level.INFO, "Дані успішно збережено у файл: {0}", filePath);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Помилка збереження у файл: " + filePath, e);
        }
    }

    public Object loadResults(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Object data = ois.readObject();
            logger.log(Level.INFO, "Дані завантажено з файлу: {0}", filePath);
            return data;
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Файл не знайдено: {0}", filePath);
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Критична помилка завантаження: " + filePath, e);
        }
        return null;
    }
}