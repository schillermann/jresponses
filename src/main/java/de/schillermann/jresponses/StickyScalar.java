package de.schillermann.jresponses;

import java.io.IOException;

public final class StickyScalar<T> implements Scalar<T> {
  private final Scalar<T> origin;
  private T cache;
  private boolean pushed;

  public StickyScalar(Scalar<T> src) {
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
