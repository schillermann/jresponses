package de.schillermann.jresponses;

import java.io.IOException;

public final class BodyCursor implements Cursor {
  private final Cursor origin;
  private Source<Boolean> reached;

  public BodyCursor(final Cursor body) {
    this.origin = body;
    this.reached = new StickySource<>(new HeaderBoundary(body));
  }

  @Override
  public int current() throws IOException {
    this.reached.value();
    return this.origin.current();
  }

  @Override
  public void next() throws IOException {
    this.reached.value();
    this.origin.next();
  }

  @Override
  public boolean exists() throws IOException {
    this.reached.value();
    return this.origin.exists();
  }

  @Override
  public void rewind() throws IOException {
    this.origin.rewind();
    this.reached = new StickySource<>(new HeaderBoundary(this.origin));
  }
}
