package de.schillermann.jpages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class StickyStream extends InputStream {
  private final Source<InputStream> origin;
  private final ByteArrayOutputStream cache;
  private int position;

  public StickyStream(Source<InputStream> source) {
    this.origin = source;
    this.cache = new ByteArrayOutputStream();
    this.position = 0;
  }

  @Override
  public int read() throws IOException {
    byte[] bytes = this.cache.toByteArray();
    if (this.position < bytes.length) {
      // Read from our "memory"
      return bytes[this.position++] & 0xFF;
    }
    // Read from the real world (the network)
    int data = this.origin.value().read();
    if (data != -1) {
      this.cache.write(data);
      this.position++;
    }
    return data;
  }

  public void reset() {
    // We don't "reset" the network, we just move our pointer
    // back to the start of our memory.
    this.position = 0;
  }
}
