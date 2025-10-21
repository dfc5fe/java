import java.util.UUID;

// Абстрактний базовий клас для всіх проїзних карток
public abstract class TravelCard {
    protected final String cardId;
    protected final CardCategory category;
    protected boolean isBlocked = false;

    public TravelCard(CardCategory category) {
        // Генерується унікальний ID, ніби він записаний на картці
        this.cardId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.category = category;
    }

    // Методи, які мають реалізувати конкретні типи карток
    public abstract boolean validatePassage(); // Перевіряє, чи можливий прохід
    public abstract void deductPassage();      // Знімає поїздку / гроші
    public abstract String getCardTypeDescription(); // Для статистики

    // Спільні методи
    public String getCardId() {
        return cardId;
    }

    public CardCategory getCategory() {
        return category;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void block() {
        this.isBlocked = true;
        System.out.println("Картку " + cardId + " заблоковано.");
    }
}