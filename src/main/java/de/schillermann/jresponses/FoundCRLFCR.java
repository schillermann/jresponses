package de.schillermann.jresponses;

/**
 * Found CRLFCR.
 */
public final class FoundCRLFCR implements BoundaryState {
  @Override
  public BoundaryState next(final int b) {
    if (b == '\n') {
      return new BoundaryFinished();
    }
    return b == '\r' ? new FoundCR() : new FirstCR();
  }

  @Override
  public boolean finished() {
    return false;
  }
}
