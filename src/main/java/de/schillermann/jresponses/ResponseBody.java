package de.schillermann.jresponses;

import java.io.IOException;
import java.io.InputStream;

public final class ResponseBody implements Response {
  private final InputStream input;

  public ResponseBody(final InputStream input) {
    this.input = input;
  }

  public ResponseBody(final String text) {
    this(new InputStreamOf(text));
  }

  @Override
  public Media media(final Media media) throws IOException {
    return media.body(this.input);
  }
}
