package de.schillermann.jresponses;

/**
 * Boundary reached.
 */
public final class BoundaryFinished implements BoundaryState {
  @Override
  public BoundaryState next(final int b) {
    return this;
  }

  @Override
  public boolean finished() {
    return true;
  }
}
