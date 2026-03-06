package de.schillermann.jpages;

import java.net.Socket;

public interface Session {
  void dispatch(Socket socket);
}
