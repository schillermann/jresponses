package de.schillermann.jresponses;

/**
 * Found the first CR.
 */
public final class FoundCR implements BoundaryState {
  @Override
  public BoundaryState next(final int b) {
    if (b == '\n') {
      return new FoundCRLF();
    }
    return b == '\r' ? this : new FirstCR();
  }

  @Override
  public boolean finished() {
    return false;
  }
}
