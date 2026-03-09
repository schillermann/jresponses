package de.schillermann.jresponses;

import java.io.IOException;

/**
 * Text.
 */
public interface Text {
  /**
   * The text content.
   * 
   * @return String
   * @throws IOException If fails
   */
  String string() throws IOException;
}
