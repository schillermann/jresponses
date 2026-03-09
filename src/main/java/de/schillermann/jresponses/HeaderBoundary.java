package de.schillermann.jresponses;

import java.io.IOException;

public final class HeaderBoundary implements Scalar<Boolean> {
  private final Cursor cursor;

  public HeaderBoundary(final Cursor body) {
    this.cursor = body;
  }

  @Override
  public Boolean value() throws IOException {
    this.reach();
    return true;
  }

  public void reach() throws IOException {
    this.cursor.rewind();
    final class State {
      private int current = 0;
      void update(final int b) {
        if ((this.current == 0 || this.current == 2) && b == '\r') {
          this.current++;
        } else if ((this.current == 1 || this.current == 3) && b == '\n') {
          this.current++;
        } else {
          this.current = (b == '\r') ? 1 : 0;
        }
      }
      boolean finished() {
        return this.current >= 4;
      }
    }
    final State state = new State();
    new While(
      new Scalar<Boolean>() {
        @Override
        public Boolean value() throws IOException {
          return HeaderBoundary.this.cursor.exists() && !state.finished();
        }
      },
      new While.Action() {
        @Override
        public void apply() throws IOException {
          state.update(HeaderBoundary.this.cursor.current());
          HeaderBoundary.this.cursor.next();
        }
      }
    ).value();
  }
}
