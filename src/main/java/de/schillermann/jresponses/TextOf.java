package de.schillermann.jresponses;

import java.io.IOException;

/**
 * Text of string.
 */
public final class TextOf implements Text {
  private final String origin;

  public TextOf(final String str) {
    this.origin = str;
  }

  @Override
  public String string() throws IOException {
    return this.origin;
  }
}
