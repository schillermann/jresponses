package de.schillermann.jresponses;

import java.io.IOException;

/**
 * Request line.
 */
public interface RequestLine {
  /**
   * HTTP Method.
   * @return Method
   * @throws IOException If fails
   */
  Method method() throws IOException;

  /**
   * Request path.
   * @return Path
   * @throws IOException If fails
   */
  Path path() throws IOException;

  /**
   * Request query.
   * @return Query
   * @throws IOException If fails
   */
  Query query() throws IOException;

  /**
   * HTTP Protocol.
   * @return Protocol
   * @throws IOException If fails
   */
  Protocol protocol() throws IOException;
}
