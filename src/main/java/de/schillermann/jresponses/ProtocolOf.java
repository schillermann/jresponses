package de.schillermann.jresponses;

import java.io.IOException;

/**
 * Protocol of text.
 */
public final class ProtocolOf implements Protocol {
  private final Text origin;

  public ProtocolOf(final String text) {
    this(new TextOf(text));
  }

  public ProtocolOf(final Text text) {
    this.origin = text;
  }

  @Override
  public String string() throws IOException {
    return this.origin.string();
  }
}
