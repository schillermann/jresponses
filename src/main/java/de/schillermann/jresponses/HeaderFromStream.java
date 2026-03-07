package de.schillermann.jresponses;

import java.io.InputStream;

public final class HeaderFromStream implements Header {
  private final StickyStream stream; // A stream that remembers what it read
  private final String name;

  public HeaderFromStream(Source<InputStream> raw, String name) {
    this(new StickyStream(raw), name);
  }

  private HeaderFromStream(StickyStream sticky, String name) {
    this.stream = sticky;
    this.name = name;
  }

  @Override
  public String string() {
    // We scan the stream line by line.
    // Because it's 'Sticky', we can 'reset' to the start
    // of the header section for every search.
    this.stream.reset();
    return new HeaderLine(this.stream, this.name).value();
  }

  @Override
  public boolean exists() {
    return !this.string().isEmpty();
  }
}
