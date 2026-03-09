package de.schillermann.jresponses;

import java.io.IOException;

/**
 * Method of text.
 */
public final class MethodOf implements Method {
  private final Text origin;

  public MethodOf(final String text) {
    this(new TextOf(text));
  }

  public MethodOf(final Text text) {
    this.origin = text;
  }

  @Override
  public String string() throws IOException {
    return this.origin.string();
  }
}
