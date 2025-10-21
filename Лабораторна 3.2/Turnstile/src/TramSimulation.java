import java.time.LocalDate;

public class TramSimulation {

    public static void main(String[] args) {
        // Створення реєстру та турнікету
        CardRegistry registry = new CardRegistry();
        Turnstile turnstile = new Turnstile(registry);

        System.out.println("\n--- Етап 1: Випуск карток ---");

        // Випуск карток різних типів
        TravelCard studentCard = registry.issueTermCard(CardCategory.STUDENT, 30); // Студентська на місяць
        TravelCard pupilCard = registry.issueTripCard(CardCategory.PUPIL, 10);     // Учнівська на 10 поїздок
        AccumulativeCard accumCard = registry.issueAccumulativeCard(20.0); // Накопичувальна (8.0 грн/поїздка -> 2 поїздки)

        // Картки для тестування відмов
        TravelCard expiredCard = registry.issueTermCard(CardCategory.STANDARD, -1); // Вже прострочена
        TravelCard emptyTripCard = registry.issueTripCard(CardCategory.STANDARD, 1); // Тільки на 1 поїздку

        System.out.println("\n--- Етап 2: Симуляція проходів ---");

        // Успішні проходи
        turnstile.processPassage(studentCard.getCardId()); // Дозвіл (Студент)
        turnstile.processPassage(studentCard.getCardId()); // Дозвіл (Студент)
        turnstile.processPassage(pupilCard.getCardId());   // Дозвіл (Учень, 9 поїздок)

        // Проходи по накопичувальній
        turnstile.processPassage(accumCard.getCardId());   // Дозвіл (Накопичувальна, баланс 12.0)
        turnstile.processPassage(accumCard.getCardId());   // Дозвіл (Накопичувальна, баланс 4.0)

        // Спроба відмови (не вистачає грошей)
        turnstile.processPassage(accumCard.getCardId());   // ВІДМОВА (Накопичувальна, баланс 4.0)

        // Поповнення картки
        accumCard.addBalance(50.0); // Баланс 54.0
        turnstile.processPassage(accumCard.getCardId());   // Дозвіл (Накопичувальна, баланс 46.0)

        // Спроба відмови (неіснуюча картка)
        turnstile.processPassage("FAKE-CARD-ID"); // ВІДМОВА (Невідома)

        // Спроба відмови (прострочена)
        turnstile.processPassage(expiredCard.getCardId()); // ВІДМОВА (Звичайна (Термін))

        // Використання останньої поїздки
        turnstile.processPassage(emptyTripCard.getCardId()); // Дозвіл (Звичайна (Поїздки), 0 поїздок)
        turnstile.processPassage(emptyTripCard.getCardId()); // ВІДМОВА (Звичайна (Поїздки))

        System.out.println("\n--- Етап 3: Блокування картки ---");
        registry.blockCard(studentCard.getCardId()); // Блокую студентську
        turnstile.processPassage(studentCard.getCardId()); // ВІДМОВА (Студентська (Термін))


        // Демонстрація звітів
        turnstile.printTotalReport();
        turnstile.printDetailedReport();
    }
}