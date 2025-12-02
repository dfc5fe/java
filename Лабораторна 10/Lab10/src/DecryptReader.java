import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class DecryptReader extends FilterReader {

    private final int shift;

    public DecryptReader(Reader in, char key) {
        super(in);
        this.shift = (int) key;
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        if (c == -1) return -1;
        return c - shift;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int numRead = super.read(cbuf, off, len);
        if (numRead == -1) return -1;

        for (int i = 0; i < numRead; i++) {
            cbuf[off + i] = (char) (cbuf[off + i] - shift);
        }
        return numRead;
    }
}