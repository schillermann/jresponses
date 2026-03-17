package de.schillermann.jresponses;

import java.io.IOException;
import java.util.Iterator;

/**
 * A chain of forks from an iterator.
 */
public final class ChainOfForks implements Forks {
  private final Iterator<Fork> origin;

  public ChainOfForks(final Iterator<Fork> it) {
    this.origin = it;
  }

  @Override
  public Response response(final Request req, final Response fbk) throws IOException {
    final Response res;
    if (this.origin.hasNext()) {
      res = new JoinedForks(this.origin.next(), this).response(req, fbk);
    } else {
      res = new NoForks().response(req, fbk);
    }
    return res;
  }
}
