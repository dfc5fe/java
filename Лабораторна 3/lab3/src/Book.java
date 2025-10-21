// Використовую record для простого та незмінного класу даних (POJO)
public record Book(String title, String author, String publisher, int year, int pageCount, double price) {
    // toString() генерується автоматично, але можна його перевизначити для гарнішого виводу
    @Override
    public String toString() {
        return String.format(
                "Назва: '%s', Автор: %s, Видавництво: %s, Рік: %d, Сторінок: %d, Ціна: %.2f грн",
                title, author, publisher, year, pageCount, price
        );
    }
}