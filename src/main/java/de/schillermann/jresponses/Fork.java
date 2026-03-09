package de.schillermann.jresponses;

import java.io.IOException;
import java.util.Optional;

/**
 * A logical branch in routing.
 */
public interface Fork {
  /**
   * Route the request to a response.
   * 
   * @param request The incoming request
   * @return The response if matched, or empty if not
   * @throws IOException If something goes wrong
   */
  Optional<Response> route(Request request) throws IOException;
}
