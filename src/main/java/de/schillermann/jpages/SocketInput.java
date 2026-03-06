package de.schillermann.jpages;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public final class SocketInput implements Source<InputStream> {
  private final Socket socket;

  public SocketInput(Socket skt) {
    this.socket = skt;
  }

  @Override
  public InputStream value() throws IOException {
    return this.socket.getInputStream();
  }
}
