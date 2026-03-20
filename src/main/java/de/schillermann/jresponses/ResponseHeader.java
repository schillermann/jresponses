package de.schillermann.jresponses;

import java.io.IOException;

public final class ResponseHeader implements Response {
  private final Response origin;
  private final String name;
  private final String value;

  public ResponseHeader(final Response origin, final String name, final String value) {
    this.origin = origin;
    this.name = name;
    this.value = value;
  }

  @Override
  public Media media(final Media media) throws IOException {
    return this.origin.media(
        media.header(this.name, this.value));
  }
}
