package za.co.wethinkcode.robotworlds.server.configuration;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The TeeOutputStream class allows writing to two output streams simultaneously.
 * It is useful for redirecting output to both a console and a log file.
 */
public class TeeOutputStream extends OutputStream {
    private final OutputStream out1;
    private final OutputStream out2;

    /**
     * Constructs a TeeOutputStream with two output streams.
     *
     * @param out1 the first output stream.
     * @param out2 the second output stream.
     */
    public TeeOutputStream(OutputStream out1, OutputStream out2) {
        this.out1 = out1;
        this.out2 = out2;
    }

    /**
     * Writes the specified byte to both output streams.
     *
     * @param b the byte to be written.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void write(int b) throws IOException {
        out1.write(b);
        out2.write(b);
    }

    /**
     * Writes b.length bytes from the specified byte array to both output streams.
     *
     * @param b the data.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void write(byte[] b) throws IOException {
        out1.write(b);
        out2.write(b);
    }

    /**
     * Writes len bytes from the specified byte array starting at offset off to both output streams.
     *
     * @param b   the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        out1.write(b, off, len);
        out2.write(b, off, len);
    }

    /**
     * Flushes both output streams.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void flush() throws IOException {
        out1.flush();
        out2.flush();
    }

    /**
     * Closes both output streams.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void close() throws IOException {
        out1.close();
        out2.close();
    }
}
