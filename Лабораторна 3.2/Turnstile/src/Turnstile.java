import java.util.HashMap;
import java.util.Map;

// Модель процесора турнікету
public class Turnstile {

    // "Зв'язок" з реєстром
    private CardRegistry registry;

    // Лічильники для статистики
    private int totalAllowed = 0;
    private int totalDenied = 0;

    // Статистика по типах карток
    private Map<String, Integer> allowedByType = new HashMap<>();
    private Map<String, Integer> deniedByType = new HashMap<>();

    // Спеціальний тип для карток, які не знайдено в реєстрі
    private static final String UNKNOWN_CARD_TYPE = "Невідома/Підроблена картка";

    public Turnstile(CardRegistry registry) {
        this.registry = registry;
    }

    // Головний метод обробки проходу
    public void processPassage(String cardId) {
        // Імітація зчитування: шукаємо картку в реєстрі
        TravelCard card = registry.findCardById(cardId);

        // Перевірка, чи картка існує (чи вдалося зчитати)
        if (card == null) {
            System.out.println("ВІДМОВА: Картку " + cardId + " не знайдено в системі.");
            logDenial(UNKNOWN_CARD_TYPE);
            return;
        }

        // Перевірка валідності самої картки (термін, поїздки, баланс)
        if (card.validatePassage()) {
            // Прохід дозволено
            card.deductPassage(); // Знімаємо поїздку/гроші
            System.out.println("ДОЗВІЛ: Прохід дозволено. (ID: " + card.getCardId() + ")");
            logAllowance(card.getCardTypeDescription());
        } else {
            // Прохід заборонено
            System.out.println("ВІДМОВА: Картка " + card.getCardId() + " недійсна (прострочена, немає поїздок/коштів, або заблокована).");
            logDenial(card.getCardTypeDescription());
        }
    }

    // --- Методи для статистики ---

    private void logAllowance(String cardType) {
        totalAllowed++;
        allowedByType.merge(cardType, 1, Integer::sum);
    }

    private void logDenial(String cardType) {
        totalDenied++;
        deniedByType.merge(cardType, 1, Integer::sum);
    }

    // Видача сумарних даних
    public void printTotalReport() {
        System.out.println("\n--- ЗАГАЛЬНИЙ ЗВІТ ТУРНІКЕТУ ---");
        System.out.println("Всього дозволено проходів: " + totalAllowed);
        System.out.println("Всього відмовлено у проході: " + totalDenied);
        System.out.println("Загальна кількість спроб:   " + (totalAllowed + totalDenied));
        System.out.println("---------------------------------");
    }

    // Видача даних з розбивкою по типах
    public void printDetailedReport() {
        System.out.println("\n--- ДЕТАЛЬНИЙ ЗВІТ ТУРНІКЕТУ ---");

        System.out.println("\nДозволено по типах карток:");
        if (allowedByType.isEmpty()) {
            System.out.println("  (немає даних)");
        }
        allowedByType.forEach((type, count) ->
                System.out.println("  " + type + ": " + count + " проходів")
        );

        System.out.println("\nВідмовлено по типах карток:");
        if (deniedByType.isEmpty()) {
            System.out.println("  (немає даних)");
        }
        deniedByType.forEach((type, count) ->
                System.out.println("  " + type + ": " + count + " відмов")
        );
        System.out.println("---------------------------------");
    }
}