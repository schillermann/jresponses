package de.schillermann.jresponses;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Response with 200 OK status line.
 */
public final class ResponseStatusLineOk implements Response {

  /**
   * The origin.
   */
  private final Response origin;

  /**
   * Ctor.
   * @param res Original response
   */
  public ResponseStatusLineOk(final Response res) {
    this.origin = new ResponseStatusLine(res, 200, "OK");
  }

  @Override
  public void printTo(final OutputStream out) throws IOException {
    this.origin.printTo(out);
  }
}
