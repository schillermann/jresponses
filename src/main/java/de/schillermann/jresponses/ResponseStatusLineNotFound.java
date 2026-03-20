package de.schillermann.jresponses;

import java.io.IOException;

/**
 * Response with 404 Not Found status line.
 */
public final class ResponseStatusLineNotFound implements Response {

  /**
   * The origin.
   */
  private final Response origin;

  /**
   * Ctor.
   * @param res Original response
   */
  public ResponseStatusLineNotFound(final Response res) {
    this.origin = new ResponseStatusLine(res, 404, "Not Found");
  }

  @Override
  public Media media(final Media media) throws IOException {
    return this.origin.media(media);
  }
}
