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

  public void listen() {
    try (final ServerSocket server = new ServerSocket(this.port)) {
      while (!Thread.currentThread().isInterrupted()) {
        try (final Socket socket = server.accept()) {
          this.session.dispatch(socket);
        } catch (IOException ex) {
          throw new IllegalStateException(
              String.format("Failed to accept a connection on port %d", this.port), ex);
        }
      }
    } catch (IOException ex) {
      throw new IllegalStateException(
          String.format("Failed to open server socket on port %d", this.port), ex);
    }
  }
}
