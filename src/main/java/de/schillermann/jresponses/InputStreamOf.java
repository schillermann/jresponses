package de.schillermann.jresponses;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public final class InputStreamOf extends InputStream {
  private final InputStream origin;

  public InputStreamOf(String text, Charset charset) {
    this(new ByteArrayInputStream(text.getBytes(charset)));
  }

  public InputStreamOf(String text) {
    this(text, Charset.defaultCharset());
  }

  public InputStreamOf(byte[] bytes) {
    this(new ByteArrayInputStream(bytes));
  }

  public InputStreamOf(InputStream stream) {
    this.origin = stream;
  }

  @Override
  public int read() throws IOException {
    return this.origin.read();
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    return this.origin.read(b, off, len);
  }

  @Override
  public void close() throws IOException {
    this.origin.close();
  }
}
