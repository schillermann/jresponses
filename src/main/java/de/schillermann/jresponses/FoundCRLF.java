package de.schillermann.jresponses;

/**
 * Found CRLF.
 */
public final class FoundCRLF implements BoundaryState {
  @Override
  public BoundaryState next(final int b) {
    return b == '\r' ? new FoundCRLFCR() : new SeekingCR();
  }

  @Override
  public boolean finished() {
    return false;
  }
}
