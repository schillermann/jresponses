package de.schillermann.jresponses;

import java.io.IOException;

public interface Response {
  /**
   * Represent this response in the media.
   * 
   * @param media The media
   * @return The updated media
   * @throws IOException If something goes wrong
   */
  Media media(Media media) throws IOException;
}
