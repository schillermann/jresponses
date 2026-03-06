package de.schillermann.jpages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class InputStreamOf extends InputStream {
  private final InputStream origin;

  // Primary Constructor
  public InputStreamOf(InputStream stream) {
    this.origin = stream;
  }

  // Secondary Constructor: From String (Encapsulates Encoding)
  public InputStreamOf(String text) {
    this(text, StandardCharsets.UTF_8);
  }

  // Secondary Constructor: From String with specific Charset
  public InputStreamOf(String text, Charset charset) {
    this(new ByteArrayInputStream(text.getBytes(charset)));
  }

  // Secondary Constructor: From Bytes
  public InputStreamOf(byte[] bytes) {
    this(new ByteArrayInputStream(bytes));
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
