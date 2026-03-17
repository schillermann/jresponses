package de.schillermann.jresponses;

import java.io.IOException;

/**
 * The outcome of a string comparison.
 */
public final class Equality implements Scalar<Choice> {
  private final String first;
  private final String second;

  public Equality(final String a, final String b) {
    this.first = a;
    this.second = b;
  }

  @Override
  public Choice value() throws IOException {
    return this.first.equals(this.second) ? new Positive() : new Negative();
  }
}
