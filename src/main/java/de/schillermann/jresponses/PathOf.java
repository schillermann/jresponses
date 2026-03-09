package de.schillermann.jresponses;

import java.io.IOException;

/**
 * Path of text.
 */
public final class PathOf implements Path {
  private final Text origin;

  public PathOf(final String text) {
    this(new TextOf(text));
  }

  public PathOf(final Text text) {
    this.origin = text;
  }

  @Override
  public String string() throws IOException {
    return this.origin.string();
  }
}
