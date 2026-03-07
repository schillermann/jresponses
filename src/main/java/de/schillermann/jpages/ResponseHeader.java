package de.schillermann.jpages;

import java.io.IOException;
import java.io.OutputStream;

public final class ResponseHeader implements Response {
  private final Response origin;
  private final String name;
  private final String value;

  public ResponseHeader(Response page, String name, String value) {
    this.origin = page;
    this.name = name;
    this.value = value;
  }

  @Override
  public void printTo(OutputStream out) throws IOException {
    out.write(
        String.format("%s: %s\r\n", this.name, this.value).getBytes());
    this.origin.printTo(out);
  }
}
