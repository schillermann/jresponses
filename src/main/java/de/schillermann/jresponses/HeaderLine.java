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
      int b;
      while ((b = this.source.read()) != -1) {
        if (b == '\n') {
          String line = buffer.toString("UTF-8").trim();
          if (line.toLowerCase().startsWith(this.prefix)) {
            return line.substring(this.prefix.length()).trim();
          }
          // End of headers section (HTTP headers end with a blank line)
          if (line.isEmpty())
            break;
          buffer.reset();
        } else if (b != '\r') {
          buffer.write(b);
        }
      }
    } catch (IOException ex) {
      throw new IllegalStateException("Failed to read header", ex);
    }
    return ""; // Null is evil; return an empty object/string instead.
  }
}
