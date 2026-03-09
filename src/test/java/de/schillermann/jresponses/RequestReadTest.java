package de.schillermann.jresponses;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestReadTest {
  @Test
  void testHangOnBodyRead() throws IOException, InterruptedException {
    // Simulating a socket stream that doesn't close but stops sending data.
    InputStream slowStream = new InputStream() {
      private byte[] data = "POST / HTTP/1.1\r\nContent-Length: 5\r\n\r\nHello".getBytes();
      private int pos = 0;
      private boolean closed = false;

      @Override
      public int read() throws IOException {
        if (pos < data.length) {
          return data[pos++] & 0xFF;
        }
        // Simulate blocking instead of returning -1 (EOF)
        while (!closed) {
          try {
            Thread.sleep(10);
          } catch (InterruptedException e) {
            return -1;
          }
        }
        return -1;
      }

      @Override
      public void close() {
        closed = true;
      }
    };

    Request request = new RequestFromStream(
        new StickyCursor(
            new CursorFromStream(() -> slowStream),
            new MemoryInBytes()));

    AtomicBoolean finished = new AtomicBoolean(false);
    Thread t = new Thread(() -> {
      try {
        byte[] body = request.body().readAllBytes();
        if ("Hello".equals(new String(body))) {
          finished.set(true);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    t.start();
    t.join(1000); // Wait 1 second

    boolean wasFinished = finished.get();
    slowStream.close();
    t.join();

    assertTrue(wasFinished, "Reading the body should not hang!");
  }

  @Test
  void requestLineReturnsText() throws IOException {
    final String data = "GET /path?query HTTP/1.1\r\n\r\n";
    final Cursor cursor = new Cursor() {
      private int pos = 0;
      @Override public int current() { return pos < data.length() ? data.charAt(pos) : -1; }
      @Override public void next() { pos++; }
      @Override public boolean exists() { return pos < data.length(); }
      @Override public void rewind() { pos = 0; }
    };
    final RequestLine line = new RequestLineFromCursor(cursor);
    assertEquals("GET", line.method().string());
    assertEquals("/path", line.path().string());
    assertEquals("query", line.query().string());
    assertEquals("HTTP/1.1", line.protocol().string());
  }
}
