package de.schillermann.jresponses;

import java.io.InputStream;

public final class MediaToStream implements Media {
  private final Output out;

  public MediaToStream(Output out) {
    this.out = out;
  }

  @Override
  public void withStatus(int code, String message) {
    this.out.print(String.format("HTTP/1.1 %d %s\r\n", code, message));
  }

  @Override
  public void withHeader(String name, String value) {
    this.out.print(String.format("%s: %s\r\n", name, value));
  }

  @Override
  public void withBody(InputStream stream) {
    this.out.print("\r\n"); // The mandatory blank line before body
    this.out.receive(stream);
  }
}
