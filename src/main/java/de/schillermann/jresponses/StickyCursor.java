package de.schillermann.jresponses;

import java.io.IOException;

public final class StickyCursor implements Cursor {
  private final Cursor origin;
  private final Memory memory;
  private int position;
  private int advanced;

  public StickyCursor(Cursor origin, Memory memory) {
    this.origin = origin;
    this.memory = memory;
    this.position = 0;
    this.advanced = 0;
  }

  @Override
  public int current() throws IOException {
    if (this.position >= this.memory.size()) {
      this.memory.remember(this.origin.current());
    }
    return this.memory.recall(this.position);
  }

  @Override
  public void next() throws IOException {
    if (this.position == this.advanced) {
      this.origin.next();
      this.advanced++;
    }
    this.position++;
  }

  @Override
  public boolean exists() throws IOException {
    return this.position < this.memory.size() || this.origin.exists();
  }

  public void rewind() {
    this.position = 0;
  }
}
