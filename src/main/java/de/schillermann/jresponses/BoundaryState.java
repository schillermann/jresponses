package de.schillermann.jresponses;

/**
 * State of the header boundary search.
 */
public interface BoundaryState {
  /**
   * Next state based on the byte.
   * 
   * @param b The byte
   * @return Next state
   */
  BoundaryState next(int b);

  /**
   * Is it the end of the boundary?
   * 
   * @return True if finished
   */
  boolean finished();
}
