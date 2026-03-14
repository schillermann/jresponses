package de.schillermann.jresponses;

import java.io.IOException;

/**
 * Recursive search for the boundary.
 */
public final class BoundaryReached implements Scalar<Boolean> {
  private final Cursor origin;
  private final BoundaryState state;

  public BoundaryReached(final Cursor cursor, final BoundaryState st) {
    this.origin = cursor;
    this.state = st;
  }

  @Override
  public Boolean value() throws IOException {
    if (this.state.finished()) {
      return true;
    }
    if (!this.origin.exists()) {
      return false;
    }
    final int b = this.origin.current();
    this.origin.next();
    return new BoundaryReached(this.origin, this.state.next(b)).value();
  }
}
