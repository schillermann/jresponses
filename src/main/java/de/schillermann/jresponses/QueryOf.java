package de.schillermann.jresponses;

import java.io.IOException;

/**
 * Query of text.
 */
public final class QueryOf implements Query {
  private final Text origin;

  public QueryOf(final String text) {
    this(new TextOf(text));
  }

  public QueryOf(final Text text) {
    this.origin = text;
  }

  @Override
  public String string() throws IOException {
    return this.origin.string();
  }
}
