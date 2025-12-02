import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class EncryptWriter extends FilterWriter {

    private final int shift;

    public EncryptWriter(Writer out, char key) {
        super(out);
        this.shift = (int) key;
    }

    @Override
    public void write(int c) throws IOException {
        super.write(c + shift);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = 0; i < len; i++) {
            write(cbuf[off + i]);
        }
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.toCharArray(), off, len);
    }
}