package de.schillermann.jresponses;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class HeaderLine {
  private final InputStream source;
  private final String prefix;

  public HeaderLine(InputStream stream, String key) {
    this.source = stream;
    this.prefix = key.toLowerCase() + ":";
  }

  public String value() {
    try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
      final class State {
        private String header = "";
        private boolean done = false;
        private int next = -1;
        void step() throws IOException {
          this.next = HeaderLine.this.source.read();
          if (this.next == -1) {
            this.done = true;
          } else if (this.next == '\n') {
            final String line = buffer.toString("UTF-8").trim();
            if (line.toLowerCase().startsWith(HeaderLine.this.prefix)) {
              this.header = line.substring(HeaderLine.this.prefix.length()).trim();
              this.done = true;
            } else if (line.isEmpty()) {
              this.done = true;
            } else {
              buffer.reset();
            }
          } else if (this.next != '\r') {
            buffer.write(this.next);
          }
        }
        boolean finished() {
          return this.done;
        }
        String found() {
          return this.header;
        }
      }
      final State state = new State();
      new While(
        new Scalar<Boolean>() {
          @Override
          public Boolean value() throws IOException {
            return !state.finished();
          }
        },
        new While.Action() {
          @Override
          public void apply() throws IOException {
            state.step();
          }
        }
      ).value();
      return state.found();
    } catch (IOException ex) {
      throw new IllegalStateException("Failed to read header", ex);
    }
  }
}
