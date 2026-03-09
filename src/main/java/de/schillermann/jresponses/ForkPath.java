package de.schillermann.jresponses;

import java.io.IOException;
import java.util.Optional;

/**
 * Matches a specific request path.
 */
public final class ForkPath implements Fork {

  /**
   * The path to match.
   */
  private final String path;

  /**
   * The response to return if matched.
   */
  private final Response response;

  /**
   * Ctor.
   * @param path Path to match
   * @param res Response to return
   */
  public ForkPath(final String path, final Response res) {
    this.path = path;
    this.response = res;
  }

  @Override
  public Optional<Response> route(final Request req) throws IOException {
    final Optional<Response> result;
    if (this.path.equals(req.requestLine().path().string())) {
      result = Optional.of(this.response);
    } else {
      result = Optional.empty();
    }
    return result;
  }
}
