package de.schillermann.jresponses;

import java.io.IOException;

/**
 * The entry point of the application.
 */
public interface Front {
  /**
   * Wait for the front-end to finish its work.
   * @return The conclusion of the process
   * @throws IOException If fails
   */
  Object conclusion() throws IOException;
}
