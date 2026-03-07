package de.schillermann.jpages;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class ResponseBody implements Response {
  private final InputStream input;

  public ResponseBody(InputStream input) {
    this.input = input;
  }

  public ResponseBody(String text) {
    this(new InputStreamOf(text));
  }

  @Override
  public void printTo(OutputStream out) throws IOException {
    out.write("\r\n".getBytes());
    this.input.transferTo(out);
  }
}
