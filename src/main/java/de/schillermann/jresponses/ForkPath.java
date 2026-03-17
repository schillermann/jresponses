package de.schillermann.jresponses;

import java.io.IOException;

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
  public Response response(final Request req, final Response fallback) throws IOException {
    return new ChoiceResponse(
      new Equality(this.path, req.requestLine().path().string()),
      this.response,
      fallback
    );
  }
}
