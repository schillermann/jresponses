package de.schillermann.jpages;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public final class Front {
  private final Session session;
  private final int port;

  public Front(Session session, int port) {
    this.session = session;
    this.port = port;
  }

  public void listen() throws IOException {
    try (final ServerSocket server = new ServerSocket(this.port)) {
      while (!Thread.currentThread().isInterrupted()) {
        try (final Socket socket = server.accept()) {
          this.session.dispatch(socket);
        }
      }
    }
  }
}
