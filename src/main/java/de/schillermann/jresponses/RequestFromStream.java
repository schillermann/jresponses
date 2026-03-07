package de.schillermann.jresponses;

import java.io.InputStream;

public final class RequestFromStream implements Request {
  private final Source<InputStream> source;

  public RequestFromStream(Source<InputStream> stream) {
    this.source = stream;
  }

  @Override
  public Header header(String name) {
    return new HeaderFromStream(this.source, name);
  }

  @Override
  public InputStream body() {
    return new OnlyBodyStream(this.source);
  }
}
