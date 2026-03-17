package de.schillermann.jresponses;

import java.util.Iterator;

/**
 * Does an iterator have more elements?
 */
public final class IteratorContinuity implements Scalar<Choice> {
  private final Iterator<?> origin;

  public IteratorContinuity(final Iterator<?> it) {
    this.origin = it;
  }

  @Override
  public Choice value() {
    return this.origin.hasNext() ? new Positive() : new Negative();
  }
}
