package de.schillermann.jresponses;

import java.io.IOException;

/**
 * A sequence of forks from an iterable.
 */
public final class ForksOf implements Forks {
  private final Iterable<Fork> origin;

  public ForksOf(final Iterable<Fork> forks) {
    this.origin = forks;
  }

  @Override
  public Response response(final Request req, final Response fbk) throws IOException {
    return new ChainOfForks(this.origin.iterator()).response(req, fbk);
  }
}
