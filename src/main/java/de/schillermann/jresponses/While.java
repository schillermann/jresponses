package de.schillermann.jresponses;

import java.io.IOException;

/**
 * A declarative representation of a loop.
 */
public final class While implements Scalar<Boolean> {

  private final Scalar<Boolean> condition;
  private final Action body;

  public While(final Scalar<Boolean> condition, final Action body) {
    this.condition = condition;
    this.body = body;
  }

  @Override
  public Boolean value() throws IOException {
    while (this.condition.value()) {
      this.body.apply();
    }
    return true;
  }

  /**
   * Action to be performed in the loop.
   */
  public interface Action {
    /**
     * Apply the action.
     * 
     * @throws IOException If fails
     */
    void apply() throws IOException;
  }
}
