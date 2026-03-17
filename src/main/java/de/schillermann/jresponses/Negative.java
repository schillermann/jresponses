package de.schillermann.jresponses;

/**
 * A negative choice.
 */
public final class Negative implements Choice {
  @Override
  public Response outcome(final Response success, final Response failure) {
    return failure;
  }
}
