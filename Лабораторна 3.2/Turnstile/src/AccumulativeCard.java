// Накопичувальна картка (d)
public class AccumulativeCard extends TravelCard {
    public static final double FARE_PRICE = 8.0; // Вартість однієї поїздки
    private double balance;

    public AccumulativeCard(double initialBalance) {
        // За умовою, такі картки можуть бути тільки "звичайного" типу
        super(CardCategory.STANDARD);
        this.balance = initialBalance;
    }

    public void addBalance(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Картку " + cardId + " поповнено на " + amount + " грн. Новий баланс: " + balance);
        }
    }

    @Override
    public boolean validatePassage() {
        // Перевірка чи не заблокована і чи достатньо грошей на 1 поїздку
        return !isBlocked() && balance >= FARE_PRICE;
    }

    @Override
    public void deductPassage() {
        if (validatePassage()) {
            this.balance -= FARE_PRICE;
        }
    }

    @Override
    public String getCardTypeDescription() {
        // Цей тип не має категорій "Учнівська" чи "Студентська"
        return "Накопичувальна";
    }

    public double getBalance() {
        return balance;
    }
}