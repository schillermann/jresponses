package de.schillermann.jresponses;

import java.io.IOException;

public final class ContentLengthHeader implements Source<Long> {
  private final Cursor cursor;

  public ContentLengthHeader(final Cursor body) {
    this.cursor = body;
  }

  @Override
  public Long value() {
    try {
      final String text = new HeaderFromCursor(this.cursor, "Content-Length").string();
      if (text.isEmpty()) {
        return -1L;
      }
      return Long.parseLong(text);
    } catch (final IOException | NumberFormatException ignore) {
      return -1L;
    }
  }
}
