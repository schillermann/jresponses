package de.schillermann.jresponses;

import java.io.IOException;

public interface Header {
  /**
   * The value of the header.
   * 
   * @return The text value
   * @throws IOException If I/O fails
   */
  String string() throws IOException;

  /**
   * Check if header exists.
   * 
   * @return True if present
   * @throws IOException If I/O fails
   */
  boolean exists() throws IOException;
}
