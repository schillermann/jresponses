package de.schillermann.jresponses;

import java.io.IOException;
import java.io.OutputStream;

public final class ResponseStatusLine implements Response {
  private final Response origin;
  private final int code;
  private final String message;

  public ResponseStatusLine(Response page, int code, String msg) {
    this.origin = page;
    this.code = code;
    this.message = msg;
  }

  @Override
  public void printTo(OutputStream out) throws IOException {
    out.write(
        String.format("HTTP/1.1 %d %s\r\n", this.code, this.message).getBytes());
    this.origin.printTo(out);
  }
}
