public class Main {
    public static void main(String[] args) {
        BookService model = new BookService();
        BookView view = new BookView();
        BookController controller = new BookController(model, view);

        controller.run();
    }
}