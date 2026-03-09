package de.schillermann.jresponses;

import java.io.IOException;

public interface Line {
  /**
   * The text content of the line.
   * 
   * @return The string representation
   * @throws IOException If the cursor fails
   */
  String string() throws IOException;
}
