package de.schillermann.jresponses;

/**
 * Looking for the first CR.
 */
public final class FirstCR implements BoundaryState {
  @Override
  public BoundaryState next(final int b) {
    return b == '\r' ? new FoundCR() : this;
  }

  @Override
  public boolean finished() {
    return false;
  }
}
