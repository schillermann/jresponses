package de.schillermann.jpages;

import java.io.IOException;
import java.net.Socket;

public interface Session {
  void dispatch(Socket socket) throws IOException;
}
