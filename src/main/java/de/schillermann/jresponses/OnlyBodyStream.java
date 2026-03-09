package de.schillermann.jresponses;

import java.io.IOException;
import java.io.InputStream;

public final class OnlyBodyStream extends InputStream {
  private final Cursor cursor;
  private final Source<Long> source;
  private long length;
  private long count;
  private boolean initialized;

  public OnlyBodyStream(final Cursor body) {
    this(
        new BodyCursor(body),
        new StickySource<>(new ContentLengthHeader(body)));
  }

  public OnlyBodyStream(final Cursor body, final Source<Long> len) {
    this.cursor = body;
    this.source = len;
    this.length = -1L;
    this.count = 0L;
    this.initialized = false;
  }

  @Override
  public int read() throws IOException {
    if (!this.initialized) {
      this.length = this.source.value();
      this.initialized = true;
    }
    final int result;
    if (this.length >= 0 && this.count >= this.length) {
      result = -1;
    } else if (this.cursor.exists()) {
      result = this.cursor.current();
      this.count++;
      if (this.length < 0 || this.count < this.length) {
        this.cursor.next();
      }
    } else {
      result = -1;
    }
    return result;
  }
}
