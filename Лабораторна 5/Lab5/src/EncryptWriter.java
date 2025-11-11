import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class EncryptWriter extends FilterWriter {

    private final int shift; // Код ключового символу

    public EncryptWriter(Writer out, char key) {
        super(out); // Передає потік "далі" (напр. FileWriter)
        this.shift = (int) key;
    }

    @Override
    public void write(int c) throws IOException {
        super.write(c + shift); // Алгоритм шифрування: код + зсув
    }

    // Перевизначення інших методів для коректної роботи
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = 0; i < len; i++) {
            write(cbuf[off + i]); // Застосував нашу логіку до кожного символу
        }
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.toCharArray(), off, len); // Зведення до попереднього
    }
}