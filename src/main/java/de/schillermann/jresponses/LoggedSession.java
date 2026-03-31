package de.schillermann.jresponses;

import java.io.IOException;
import java.net.Socket;

/**
 * Log session activity.
 */
public final class LoggedSession implements Session {
  private final Session origin;

  public LoggedSession(final Session session) {
    this.origin = session;
  }

  @Override
  public void dispatch(final Socket socket) throws IOException {
    System.out.printf(
        "New connection from %s\n",
        socket.getRemoteSocketAddress()
    );
    this.origin.dispatch(socket);
  }
}
