package de.schillermann.jresponses;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * A front-end exposed to the wire.
 */
public final class WireFront implements Front {
  private final Session session;
  private final Scalar<ServerSocket> socket;
  private final Scalar<Integer> threads;

  public WireFront(final Session sn) {
    this(
      sn,
      new ServerSocketOf(new Port()),
      new NumberOfConnections()
    );
  }

  public WireFront(final Session sn, final Scalar<ServerSocket> skt) {
    this(
      sn,
      skt,
      () -> Runtime.getRuntime().availableProcessors()
    );
  }

  public WireFront(
    final Session sn,
    final Scalar<ServerSocket> skt,
    final Scalar<Integer> thds
  ) {
    this.session = sn;
    this.socket = skt;
    this.threads = thds;
  }

  @Override
  public Object conclusion() throws IOException {
    try (final ServerSocket server = this.socket.value()) {
      return new Connections(server, this.session, this.threads).conclusion();
    }
  }
}
