package de.schillermann.jresponses;

import java.io.IOException;

/**
 * A logical branch in routing.
 */
public interface Fork {
  /**
   * Response from the fork.
   * 
   * @param request The incoming request
   * @param fallback The response if not matched
   * @return The response
   * @throws IOException If something goes wrong
   */
  Response response(Request request, Response fallback) throws IOException;
}
