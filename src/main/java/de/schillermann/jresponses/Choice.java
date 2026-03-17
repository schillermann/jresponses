package de.schillermann.jresponses;

import java.io.IOException;

/**
 * A choice between two outcomes.
 */
public interface Choice {
  /**
   * The result of the choice.
   * @param success If positive
   * @param failure If negative
   * @return The chosen response
   * @throws IOException If fails
   */
  Response outcome(Response success, Response failure) throws IOException;
}
