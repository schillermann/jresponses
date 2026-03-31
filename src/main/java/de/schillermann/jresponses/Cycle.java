package de.schillermann.jresponses;

import java.io.IOException;

/**
 * A declarative representation of a loop.
 */
public final class Cycle implements Scalar<Object> {

  private final Limit limit;
  private final Step step;

  public Cycle(final Limit limit, final Step step) {
    this.limit = limit;
    this.step = step;
  }

  @Override
  public Object value() throws IOException {
    while (this.limit.value()) {
      this.step.value();
    }
    return new End();
  }
}
