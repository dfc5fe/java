import java.time.LocalDate;

// Картка з терміном дії (b)
public class TermBasedCard extends TravelCard {
    private LocalDate expiryDate;

    public TermBasedCard(CardCategory category, int daysValid) {
        super(category);
        this.expiryDate = LocalDate.now().plusDays(daysValid);
    }

    @Override
    public boolean validatePassage() {
        // Перевірка чи картка не заблокована і чи не вийшов термін дії
        return !isBlocked() && !LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public void deductPassage() {
        // Нічого не робиться, поїздки необмежені в межах терміну дії
    }

    @Override
    public String getCardTypeDescription() {
        return category.toString() + " (Термін дії)";
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}