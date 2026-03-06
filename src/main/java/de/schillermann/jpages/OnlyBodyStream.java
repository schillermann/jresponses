package de.schillermann.jpages;

import java.io.IOException;
import java.io.InputStream;

public final class OnlyBodyStream extends InputStream {
  private final Source<InputStream> origin;
  private boolean found = false;

  public OnlyBodyStream(Source<InputStream> stream) {
    this.origin = stream;
  }

  @Override
  public int read() throws IOException {
    if (!this.found) {
      this.skipHeaders();
      this.found = true;
    }
    return this.origin.value().read();
  }

  private void skipHeaders() throws IOException {
    int state = 0; // State machine to track \r\n\r\n
    while (state < 4) {
      int b = this.origin.value().read();
      if (b == -1)
        break;

      // HTTP boundary is specifically: 13, 10, 13, 10 (\r\n\r\n)
      if ((state == 0 || state == 2) && b == '\r') {
        state++;
      } else if ((state == 1 || state == 3) && b == '\n') {
        state++;
      } else {
        state = (b == '\r') ? 1 : 0;
      }
    }
  }
}
