package de.schillermann.jresponses;

import java.io.IOException;

/**
 * A joined fork and the rest.
 */
public final class JoinedForks implements Forks {
  private final Fork head;
  private final Forks tail;

  public JoinedForks(final Fork fork, final Forks rest) {
    this.head = fork;
    this.tail = rest;
  }

  @Override
  public Response response(final Request req, final Response fallback) throws IOException {
    // The fork itself decides whether to match or 
    // to pass the "tail" as the next fallback.
    return this.head.response(
      req, 
      new LazyResponse(new Scalar<Response>() {
        @Override
        public Response value() throws IOException {
          return JoinedForks.this.tail.response(req, fallback);
        }
      })
    );
  }
}
