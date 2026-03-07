package de.schillermann.jresponses;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public final class SocketOutput implements Source<OutputStream> {
  private final Socket socket;

  public SocketOutput(Socket skt) {
    this.socket = skt;
  }

  @Override
  public OutputStream value() throws IOException {
    return this.socket.getOutputStream();
  }
}
