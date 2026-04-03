package de.schillermann.jresponses;

import java.io.IOException;

public final class RequestLineFromCursor implements RequestLine {
  private final Scalar<String[]> parts;

  public RequestLineFromCursor(final Cursor crsr) {
    this.parts = new StickyScalar<>(
      () -> {
        crsr.rewind();
        return new LineFromCursor(crsr).string().split(" ", 3);
      }
    );
  }

  @Override
  public Method method() throws IOException {
    final String[] p = this.parts.value();
    final String name;
    if (p.length > 0) {
      name = p[0];
    } else {
      name = "";
    }
    return new MethodOf(name);
  }

  @Override
  public Path path() throws IOException {
    final String[] p = this.parts.value();
    final String full;
    if (p.length > 1) {
      full = p[1];
    } else {
      full = "";
    }
    final int idx = full.indexOf('?');
    final String path;
    if (idx == -1) {
      path = full;
    } else {
      path = full.substring(0, idx);
    }
    return new PathOf(path);
  }

  @Override
  public Query query() throws IOException {
    final String[] p = this.parts.value();
    final String full;
    if (p.length > 1) {
      full = p[1];
    } else {
      full = "";
    }
    final int idx = full.indexOf('?');
    final String query;
    if (idx == -1) {
      query = "";
    } else {
      query = full.substring(idx + 1);
    }
    return new QueryOf(query);
  }

  @Override
  public Protocol protocol() throws IOException {
    final String[] p = this.parts.value();
    final String name;
    if (p.length > 2) {
      name = p[2];
    } else {
      name = "";
    }
    return new ProtocolOf(name);
  }
}
