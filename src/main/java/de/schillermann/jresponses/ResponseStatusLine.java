package de.schillermann.jresponses;

import java.io.IOException;

public final class ResponseStatusLine implements Response {
  private final Response origin;
  private final int code;
  private final String message;

  public ResponseStatusLine(final Response origin, final int code, final String msg) {
    this.origin = origin;
    this.code = code;
    this.message = msg;
  }

  @Override
  public Media media(final Media media) throws IOException {
    return this.origin.media(
        media.status(this.code, this.message));
  }
}
