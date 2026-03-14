package de.schillermann.jresponses;

import java.io.IOException;

/**
 * Text from a cursor until a delimiter is found.
 */
public final class TextUntil implements Scalar<String> {
  private final Cursor cursor;
  private final int delimiter;

  public TextUntil(final Cursor crsr, final int delim) {
    this.cursor = crsr;
    this.delimiter = delim;
  }

  @Override
  public String value() throws IOException {
    if (!this.cursor.exists() || this.cursor.current() == this.delimiter) {
      return "";
    }
    final char c = (char) this.cursor.current();
    this.cursor.next();
    return c + this.value();
  }
}
