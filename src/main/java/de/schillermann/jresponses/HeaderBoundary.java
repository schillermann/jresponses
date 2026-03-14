package de.schillermann.jresponses;

import java.io.IOException;

/**
 * Reaches the boundary of the header section (\r\n\r\n).
 */
public final class HeaderBoundary implements Scalar<Boolean> {
  private final Cursor cursor;

  public HeaderBoundary(final Cursor body) {
    this.cursor = body;
  }

  @Override
  public Boolean value() throws IOException {
    this.cursor.rewind();
    return new BoundaryReached(this.cursor, new SeekingFirstCR()).value();
  }
}
