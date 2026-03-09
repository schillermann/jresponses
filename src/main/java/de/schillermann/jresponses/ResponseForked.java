package de.schillermann.jresponses;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Optional;

/**
 * Response that chooses its behavior based on forks.
 */
public final class ResponseForked implements Response {

  private final Request request;
  private final Iterable<Fork> forks;
  private final Response fallback;

  public ResponseForked(final Request req, final Response fallback, final Fork... forks) {
    this(req, Arrays.asList(forks), fallback);
  }

  public ResponseForked(final Request req, final Iterable<Fork> forks, final Response fallback) {
    this.request = req;
    this.forks = forks;
    this.fallback = fallback;
  }

  @Override
  public void printTo(final OutputStream out) throws IOException {
    Response target = this.fallback;
    for (final Fork fork : this.forks) {
      final Optional<Response> opt = fork.route(this.request);
      if (opt.isPresent()) {
        target = opt.get();
        break;
      }
    }
    target.printTo(out);
  }
}
