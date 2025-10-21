import java.util.HashMap;
import java.util.Map;

// Система, що веде реєстр виданих карток
public class CardRegistry {
    // Картки зберігаються в "базі даних" (Map)
    private Map<String, TravelCard> issuedCards = new HashMap<>();

    // Випуск картки (за терміном)
    public TravelCard issueTermCard(CardCategory category, int daysValid) {
        TermBasedCard card = new TermBasedCard(category, daysValid);
        issuedCards.put(card.getCardId(), card);
        System.out.println("Випущено картку (Термін): " + card.getCardId() + ", Тип: " + category + ", Діє до: " + card.getExpiryDate());
        return card;
    }

    // Випуск картки (за поїздками)
    public TravelCard issueTripCard(CardCategory category, int trips) {
        TripBasedCard card = new TripBasedCard(category, trips);
        issuedCards.put(card.getCardId(), card);
        System.out.println("Випущено картку (Поїздки): " + card.getCardId() + ", Тип: " + category + ", Поїздок: " + trips);
        return card;
    }

    // Випуск картки (накопичувальна)
    public AccumulativeCard issueAccumulativeCard(double initialBalance) {
        AccumulativeCard card = new AccumulativeCard(initialBalance);
        issuedCards.put(card.getCardId(), card);
        System.out.println("Випущено картку (Накопичувальна): " + card.getCardId() + ", Баланс: " + initialBalance);
        return card;
    }

    // Метод для турнікета, щоб "зчитати" картку
    // В реальності дані б читалися з самої картки, але тут іде імітація
    // перевірки, що картка взагалі існує в системі
    public TravelCard findCardById(String cardId) {
        return issuedCards.get(cardId);
    }

    // Можливість заблокувати картку (хоча хто її тут вкраде лол)
    public void blockCard(String cardId) {
        TravelCard card = findCardById(cardId);
        if (card != null) {
            card.block();
        }
    }
}