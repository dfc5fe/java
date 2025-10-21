import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class BookController {
    private final BookService model;
    private final BookView view;
    private final Scanner scanner;
    private final Random random = new Random();

    // Підготовлені дані для пошуку
    private final String[] authorsToSearch = {"Тарас Шевченко", "Іван Франко", "Неіснуючий Автор"};
    private final String[] publishersToSearch = {"Віват", "А-БА-БА-ГА-ЛА-МА-ГА", "Неіснуюче Видавництво"};
    private final int[] yearsToSearch = {2019, 2021, 2025};

    public BookController(BookService model, BookView view) {
        this.model = model;
        this.view = view;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        // Вихідний масив для перевірки даних
        view.printBooks("Початковий список книг", model.getAllBooks());

        boolean running = true;
        while (running) {
            view.printMenu();
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера сканера

                switch (choice) {
                    case 1 -> findByAuthor();
                    case 2 -> findByPublisher();
                    case 3 -> findAfterYear();
                    case 4 -> sortAndPrintByPublisher();
                    case 0 -> running = false;
                    default -> view.printMessage("Спробуй ще раз");
                }
            } catch (InputMismatchException e) {
                view.printMessage("Потрібно ввести число.");
                scanner.nextLine();
            }
        }
        view.printMessage("Програму завершено.");
        scanner.close();
    }

    private void findByAuthor() {
        String author = authorsToSearch[random.nextInt(authorsToSearch.length)];
        view.printMessage("\n=> Пошук за автором: \"" + author + "\"");
        Book[] result = model.getBooksByAuthor(author);
        view.printBooks("Результати пошуку за автором", result);
    }

    private void findByPublisher() {
        String publisher = publishersToSearch[random.nextInt(publishersToSearch.length)];
        view.printMessage("\n=> Пошук за видавництвом: \"" + publisher + "\"");
        Book[] result = model.getBooksByPublisher(publisher);
        view.printBooks("Результати пошуку за видавництвом", result);
    }

    private void findAfterYear() {
        int year = yearsToSearch[random.nextInt(yearsToSearch.length)];
        view.printMessage("\n=> Пошук книг, виданих після " + year + " року");
        Book[] result = model.getBooksPublishedAfterYear(year);
        view.printBooks("Результати пошуку за роком видання", result);
    }

    private void sortAndPrintByPublisher() {
        view.printMessage("\n=> Сортування книг за видавництвом...");
        model.sortByPublisher();
        view.printBooks("Відсортований список книг", model.getAllBooks());
    }
}