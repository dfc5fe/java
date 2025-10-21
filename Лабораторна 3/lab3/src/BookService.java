import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BookService {
    private Book[] books;

    public BookService() {
        this.books = initializeData();
    }

    // Метод для початкової ініціалізації масиву книг
    private Book[] initializeData() {
        return new Book[]{
                new Book("Кобзар", "Тарас Шевченко", "А-БА-БА-ГА-ЛА-МА-ГА", 2021, 704, 350.0),
                new Book("Захар Беркут", "Іван Франко", "Навчальна книга - Богдан", 2019, 288, 120.5),
                new Book("Тигролови", "Іван Багряний", "Віват", 2023, 320, 180.0),
                new Book("Енеїда", "Іван Котляревський", "А-БА-БА-ГА-ЛА-МА-ГА", 2020, 304, 250.0),
                new Book("Тіні забутих предків", "Михайло Коцюбинський", "Навчальна книга - Богдан", 2018, 160, 95.75),
                new Book("Кайдашева сім'я", "Іван Нечуй-Левицький", "Віват", 2022, 256, 150.0),
                new Book("Лісова пісня", "Леся Українка", "А-БА-БА-ГА-ЛА-МА-ГА", 2022, 128, 200.0),
                new Book("Маруся Чурай", "Ліна Костенко", "Либідь", 1979, 142, 300.0),
                new Book("Vovkulaka", "Іван Франко", "Навчальна книга - Богдан", 2020, 192, 110.0),
                new Book("Місто", "Валер'ян Підмогильний", "Віват", 2021, 288, 175.25),
                new Book("Поводир", "Тарас Шевченко", "Либідь", 2015, 96, 80.0)
        };
    }

    // Отримую всі книги (для виводу початкового стану)
    public Book[] getAllBooks() {
        return books;
    }

    // Список книг зазначеного автора
    public Book[] getBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.author().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result.toArray(new Book[0]);
    }

    // Список книг, які видані зазначеним видавництвом
    public Book[] getBooksByPublisher(String publisher) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.publisher().equalsIgnoreCase(publisher)) {
                result.add(book);
            }
        }
        return result.toArray(new Book[0]);
    }

    // Список книг, виданих пізніше вказаного року
    public Book[] getBooksPublishedAfterYear(int year) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.year() > year) {
                result.add(book);
            }
        }
        return result.toArray(new Book[0]);
    }

    // Сортування за видавництвом
    public void sortByPublisher() {
        // Компаратор, за умовою
        Comparator<Book> publisherComparator = Comparator.comparing(Book::publisher);
        Arrays.sort(books, publisherComparator);
    }
}