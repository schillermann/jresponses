package de.schillermann.jresponses;

import java.io.InputStream;

public final class RequestFromStream implements Request {
  private final Cursor source;

  public RequestFromStream(Cursor input) {
    this.source = input;
  }

  @Override
  public RequestLine requestLine() {
    return new RequestLineFromCursor(this.source);
  }

  @Override
  public Header header(String name) {
    return new HeaderFromCursor(this.source, name);
  }

  @Override
  public InputStream body() {
    return new OnlyBodyStream(this.source);
  }
}
