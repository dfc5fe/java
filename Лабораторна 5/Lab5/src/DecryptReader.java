import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class DecryptReader extends FilterReader {

    private final int shift; // Код ключового символу

    public DecryptReader(Reader in, char key) {
        super(in); // Передача потоку, з якого читати (напр. FileReader)
        this.shift = (int) key;
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        if (c == -1) {
            return -1; // Кінець потоку
        } else {
            return c - shift; // Алгоритм дешифрування: код - зсув
        }
    }

    // Перевизначення інших методів для ефективності
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int charsRead = super.read(cbuf, off, len); // Читаємо "як є" (зашифровані)
        if (charsRead == -1) {
            return -1;
        }
        // Дешифрування
        for (int i = 0; i < charsRead; i++) {
            cbuf[off + i] = (char) (cbuf[off + i] - shift);
        }
        return charsRead;
    }
}