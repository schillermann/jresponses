package de.schillermann.jresponses;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A response determined by a choice.
 */
public final class ChoiceResponse implements Response {
  private final Scalar<Choice> choice;
  private final Response success;
  private final Response failure;

  public ChoiceResponse(final Scalar<Choice> ch, final Response ok, final Response fail) {
    this.choice = ch;
    this.success = ok;
    this.failure = fail;
  }

  @Override
  public void printTo(final OutputStream out) throws IOException {
    this.choice.value().outcome(this.success, this.failure).printTo(out);
  }
}
