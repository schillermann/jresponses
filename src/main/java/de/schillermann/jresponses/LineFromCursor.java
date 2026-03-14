package de.schillermann.jresponses;

import java.io.IOException;

/**
 * A line extracted from a cursor.
 */
public final class LineFromCursor implements Line {
  private final Cursor cursor;

  public LineFromCursor(final Cursor crsr) {
    this.cursor = crsr;
  }

  @Override
  public String string() throws IOException {
    final String content = new TextUntil(this.cursor, '\r').value();
    new LineEnd(this.cursor).value();
    return content;
  }
}
