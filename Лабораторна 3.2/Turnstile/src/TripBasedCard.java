// Картка з фіксованою кількістю поїздок (c)
public class TripBasedCard extends TravelCard {
    private int remainingTrips;

    public TripBasedCard(CardCategory category, int trips) {
        super(category);
        this.remainingTrips = trips;
    }

    @Override
    public boolean validatePassage() {
        // Перевірка чи не заблокована і чи є поїздки
        return !isBlocked() && remainingTrips > 0;
    }

    @Override
    public void deductPassage() {
        if (validatePassage()) {
            this.remainingTrips--;
        }
    }

    @Override
    public String getCardTypeDescription() {
        return category.toString() + " (Поїздки)";
    }

    public int getRemainingTrips() {
        return remainingTrips;
    }
}