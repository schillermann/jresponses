package de.schillermann.jresponses;

import java.io.IOException;

public final class RequestLineFromCursor implements RequestLine {
  private final Cursor cursor;
  private String method;
  private String path;
  private String query;
  private String protocol;
  private boolean read;

  public RequestLineFromCursor(final Cursor crsr) {
    this.cursor = crsr;
    this.read = false;
  }

  @Override
  public Method method() throws IOException {
    this.read();
    return new MethodOf(this.method);
  }

  @Override
  public Path path() throws IOException {
    this.read();
    return new PathOf(this.path);
  }

  @Override
  public Query query() throws IOException {
    this.read();
    return new QueryOf(this.query);
  }

  @Override
  public Protocol protocol() throws IOException {
    this.read();
    return new ProtocolOf(this.protocol);
  }

  private void read() throws IOException {
    if (!this.read) {
      this.cursor.rewind();
      final String line = new LineFromCursor(this.cursor).string();
      final String[] parts = line.split(" ", 3);
      this.method = parts.length > 0 ? parts[0] : "";
      final String fullPath = parts.length > 1 ? parts[1] : "";
      final int idx = fullPath.indexOf('?');
      if (idx == -1) {
        this.path = fullPath;
        this.query = "";
      } else {
        this.path = fullPath.substring(0, idx);
        this.query = fullPath.substring(idx + 1);
      }
      this.protocol = parts.length > 2 ? parts[2] : "";
      this.read = true;
    }
  }
}
