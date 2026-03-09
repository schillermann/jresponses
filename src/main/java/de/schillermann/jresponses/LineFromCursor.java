package de.schillermann.jresponses;

import java.io.IOException;

public final class LineFromCursor implements Line {
  private final Cursor cursor;

  public LineFromCursor(final Cursor crsr) {
    this.cursor = crsr;
  }

  @Override
  public String string() throws IOException {
    final StringBuilder buf = new StringBuilder();
    while (this.cursor.exists()) {
      final int b = this.cursor.current();
      if (b == '\r') {
        this.cursor.next();
        if (this.cursor.exists() && this.cursor.current() == '\n') {
          this.cursor.next();
        }
        break;
      }
      buf.append((char) b);
      this.cursor.next();
    }
    return buf.toString();
  }
}
