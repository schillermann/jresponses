package de.schillermann.jresponses;

import java.io.IOException;

/**
 * A collection of forks.
 */
public interface Forks {
  /**
   * Response from the forks.
   * @param req The request
   * @param fallback The final fallback
   * @return The response
   * @throws IOException If fails
   */
  Response response(Request req, Response fallback) throws IOException;
}
