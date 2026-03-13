package de.schillermann.jresponses;

import java.io.InputStream;
import java.net.Socket;

/**
 * Request from a socket.
 */
public final class RequestFromSocket implements Request {

  private final Request origin;

  public RequestFromSocket(final Socket socket) {
    this(
        new RequestFromStream(
            new StickyCursor(
                new CursorFromStream(
                    new SocketInput(socket)),
                new MemoryInBytes())));
  }

  public RequestFromSocket(final Request origin) {
    this.origin = origin;
  }

  @Override
  public RequestLine requestLine() {
    return this.origin.requestLine();
  }

  @Override
  public Header header(final String name) {
    return this.origin.header(name);
  }

  @Override
  public InputStream body() {
    return this.origin.body();
  }
}
