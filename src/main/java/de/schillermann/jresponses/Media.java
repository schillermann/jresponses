package de.schillermann.jresponses;

import java.io.IOException;
import java.io.InputStream;

/**
 * A media for a response.
 */
public interface Media {
  /**
   * Add a status code and message.
   * @param code The status code
   * @param message The status message
   * @return This media
   * @throws IOException If something goes wrong
   */
  Media status(int code, String message) throws IOException;

  /**
   * Add a header.
   * @param name The header name
   * @param value The header value
   * @return This media
   * @throws IOException If something goes wrong
   */
  Media header(String name, String value) throws IOException;

  /**
   * Add a body.
   * @param stream The body input stream
   * @return This media
   * @throws IOException If something goes wrong
   */
  Media body(InputStream stream) throws IOException;
}
