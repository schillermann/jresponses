package de.schillermann.jresponses;

import java.io.IOException;

public final class StickySource<T> implements Source<T> {
  private final Source<T> origin;
  private T cache;
  private boolean pushed;

  public StickySource(Source<T> src) {
    this.origin = src;
    this.pushed = false;
  }

  @Override
  public T value() throws IOException {
    if (!this.pushed) {
      this.cache = this.origin.value();
      this.pushed = true;
    }
    return this.cache;
  }
}
