package de.schillermann.jresponses;

import java.io.IOException;

/**
 * An empty collection of forks.
 */
public final class NoForks implements Forks {
  @Override
  public Response response(final Request req, final Response fallback) throws IOException {
    return fallback;
  }
}
