package de.schillermann.jresponses;

import java.io.IOException;

/**
 * Log the lifecycle of a front-end.
 */
public final class LoggedFront implements Front {
  private final Front origin;

  public LoggedFront(final Front front) {
    this.origin = front;
  }

  @Override
  public Object conclusion() throws IOException {
    System.out.println("Starting JResponses front-end...");
    final long start = System.currentTimeMillis();
    final Object res = this.origin.conclusion();
    final long end = System.currentTimeMillis();
    System.out.printf(
        "JResponses front-end stopped (active for %d ms).\n",
        end - start
    );
    return res;
  }
}
