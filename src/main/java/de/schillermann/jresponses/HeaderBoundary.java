package de.schillermann.jresponses;

import java.io.IOException;

public final class HeaderBoundary implements Source<Boolean> {
  private final Cursor cursor;

  public HeaderBoundary(final Cursor body) {
    this.cursor = body;
  }

  @Override
  public Boolean value() throws IOException {
    this.reach();
    return true;
  }

  public void reach() throws IOException {
    this.cursor.rewind();
    int state = 0;
    while (this.cursor.exists() && state < 4) {
      final int b = this.cursor.current();
      if ((state == 0 || state == 2) && b == '\r') {
        state++;
      } else if ((state == 1 || state == 3) && b == '\n') {
        state++;
      } else {
        state = (b == '\r') ? 1 : 0;
      }
      this.cursor.next();
    }
  }
}
