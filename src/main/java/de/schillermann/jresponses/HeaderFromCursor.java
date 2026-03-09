package de.schillermann.jresponses;

import java.io.IOException;

public final class HeaderFromCursor implements Header {
  private final Cursor cursor;
  private final String name;

  public HeaderFromCursor(Cursor crsr, String name) {
    this.cursor = crsr;
    this.name = name;
  }

  @Override
  public String string() throws IOException {
    this.cursor.rewind();
    final class State {
      private String value = "";
      private boolean done = false;
      void process() throws IOException {
        final String line = new LineFromCursor(HeaderFromCursor.this.cursor).string();
        if (line.isEmpty()) {
          this.done = true;
        } else if (line.toLowerCase().startsWith(
            HeaderFromCursor.this.name.toLowerCase() + ":"
        )) {
          this.value = line.substring(HeaderFromCursor.this.name.length() + 1).trim();
          this.done = true;
        }
      }
      String value() {
        return this.value;
      }
      boolean finished() {
        return this.done;
      }
    }
    final State state = new State();
    new While(
      new Scalar<Boolean>() {
        @Override
        public Boolean value() throws IOException {
          return HeaderFromCursor.this.cursor.exists() && !state.finished();
        }
      },
      new While.Action() {
        @Override
        public void apply() throws IOException {
          state.process();
        }
      }
    ).value();
    return state.value();
  }

  @Override
  public boolean exists() throws IOException {
    return !this.string().isEmpty();
  }
}
