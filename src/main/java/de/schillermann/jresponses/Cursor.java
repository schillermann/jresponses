package de.schillermann.jresponses;

import java.io.IOException;

public interface Cursor {
  /**
   * The byte at the current position.
   * 
   * @return The byte (0-255)
   * @throws IOException If something goes wrong
   */
  int current() throws IOException;

  /**
   * Move the pointer to the next byte.
   * 
   * @throws IOException If no more data exists
   */
  void next() throws IOException;

  /**
   * Is there any data left?
   * 
   * @return True if we can call current() and next()
   */
  boolean exists() throws IOException;

  /**
   * Resets the cursor to the beginning of the data.
   * 
   * @throws IOException If something goes wrong during rewinding
   */
  void rewind() throws IOException;
}
