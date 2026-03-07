package de.schillermann.jresponses;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;

public final class OutputToStream implements Output {
  private final Source<OutputStream> out;

  public OutputToStream(Source<OutputStream> stream) {
    this.out = stream;
  }

  public void print(String text) {
    try {
      this.out.value().write(text.getBytes(Charset.defaultCharset()));
    } catch (IOException ex) {
      throw new UncheckedIOException("Failed to write text output", ex);
    }
  }

  public void receive(InputStream stream) {
    try {
      stream.transferTo(this.out.value());
    } catch (IOException ex) {
      throw new UncheckedIOException("Failed to write stream output", ex);
    }
  }
}
