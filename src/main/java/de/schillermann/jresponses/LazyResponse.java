package de.schillermann.jresponses;

import java.io.IOException;

/**
 * A response that is evaluated lazily.
 * 
 * <p>
 * This object is a declarative wrapper around a {@link Scalar} of a response.
 * It ensures that no work (like routing or parsing) is performed until
 * the moment {@link #media(Media)} is called.
 * This achieves that the constructors remains code-free and execution can thus
 * postponed as much as possible.
 * </p>
 */
public final class LazyResponse implements Response {
  private final Scalar<Response> origin;

  public LazyResponse(final Scalar<Response> src) {
    this.origin = src;
  }

  @Override
  public Media media(final Media media) throws IOException {
    return this.origin.value().media(media);
  }
}
