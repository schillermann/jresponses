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
    String value = "";
    while (this.cursor.exists()) {
      final String line = new LineFromCursor(this.cursor).string();
      if (line.isEmpty())
        break;

      if (line.toLowerCase().startsWith(this.name.toLowerCase() + ":")) {
        value = line.substring(this.name.length() + 1).trim();
        break;
      }
    }
    return value;
  }

  @Override
  public boolean exists() throws IOException {
    return !this.string().isEmpty();
  }
}
