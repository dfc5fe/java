public class BookView {

    public void printMenu() {
        System.out.println("\n============ МЕНЮ ============");
        System.out.println("1. Список книг зазначеного автора");
        System.out.println("2. Список книг зазначеного видавництва");
        System.out.println("3. Список книг, виданих пізніше вказаного року");
        System.out.println("4. Сортування книг за видавництвами та вивести");
        System.out.println("0. Вийти з програми");
        System.out.print("Твій вибір: ");
    }

    public void printBooks(String header, Book[] books) {
        System.out.println("\n--- " + header + " ---");
        // Перевірка, чи масив не порожній
        if (books == null || books.length == 0) {
            System.out.println("Книг за твоїм запитом не знайдено.");
        } else {
            for (int i = 0; i < books.length; i++) {
                System.out.println((i + 1) + ". " + books[i]);
            }
        }
        System.out.println("------------------------------------");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}