package de.schillermann.jresponses;

/**
 * A positive choice.
 */
public final class Positive implements Choice {
  @Override
  public Response outcome(final Response success, final Response failure) {
    return success;
  }
}
