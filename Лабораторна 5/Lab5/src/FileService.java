import java.io.*;

public class FileService {

    public void saveResults(Object data, String filePath) {
        // Використав try-with-resources для автоматичного закриття потоку
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(data); // Серіалізація об'єкта
            System.out.println("Результати успішно збережено у " + filePath);
        } catch (IOException e) {
            System.err.println("Помилка збереження файлу: " + e.getMessage());
        }
    }

    public Object loadResults(String filePath) {
        // Використав try-with-resources
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject(); // Десеріалізація об'єкта
        } catch (FileNotFoundException e) {
            System.err.println("Помилка: Файл для завантаження не знайдено - " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Помилка завантаження файлу: " + e.getMessage());
        }
        return null;
    }
}