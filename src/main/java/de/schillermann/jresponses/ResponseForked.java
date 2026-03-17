package de.schillermann.jresponses;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

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
    new LazyResponse(
      new Scalar<Response>() {
        @Override
        public Response value() throws IOException {
          return new ForksOf(ResponseForked.this.forks)
            .response(ResponseForked.this.request, ResponseForked.this.fallback);
        }
      }
    ).printTo(out);
  }
}
