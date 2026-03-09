package de.schillermann.jresponses;

import java.util.Arrays;

public final class MemoryInBytes implements Memory {
  private byte[] buffer;
  private int count;

  public MemoryInBytes() {
    this(32);
  }

  public MemoryInBytes(final int capacity) {
    this.buffer = new byte[capacity];
    this.count = 0;
  }

  @Override
  public void remember(final int data) {
    if (this.count == this.buffer.length) {
      this.buffer = Arrays.copyOf(this.buffer, this.buffer.length * 2);
    }
    this.buffer[this.count] = (byte) data;
    this.count++;
  }

  @Override
  public int recall(final int position) {
    return this.buffer[position] & 0xFF;
  }

  @Override
  public int size() {
    return this.count;
  }
}
