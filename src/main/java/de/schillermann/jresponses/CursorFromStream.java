package de.schillermann.jresponses;

import java.io.IOException;
import java.io.InputStream;

public final class CursorFromStream implements Cursor {
  private final Source<InputStream> source;
  private int head;

  public CursorFromStream(Source<InputStream> src) {
    this.source = src;
    this.head = -2;
  }

  @Override
  public int current() throws IOException {
    if (this.head == -2)
      this.next();
    return this.head;
  }

  @Override
  public void next() throws IOException {
    // The Socket is only touched here, at the very last millisecond.
    this.head = this.source.value().read();
  }

  @Override
  public boolean exists() {
    return this.head != -1;
  }

  @Override
  public void rewind() {
    throw new UnsupportedOperationException(
        "Raw socket streams cannot rewind. Wrap me in a StickyCursor first!");
  }
}
