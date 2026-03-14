package de.schillermann.jresponses;

import java.io.IOException;

/**
 * Recursive search for a specific header line.
 */
public final class HeaderFound implements Scalar<String> {
  private final Cursor cursor;
  private final String prefix;

  public HeaderFound(final Cursor crsr, final String key) {
    this.cursor = crsr;
    this.prefix = key;
  }

  @Override
  public String value() throws IOException {
    if (!this.cursor.exists()) {
      return "";
    }
    final String line = new LineFromCursor(this.cursor).string();
    if (line.isEmpty()) {
      return "";
    }
    if (line.toLowerCase().startsWith(this.prefix)) {
      return line.substring(this.prefix.length()).trim();
    }
    return this.value();
  }
}
