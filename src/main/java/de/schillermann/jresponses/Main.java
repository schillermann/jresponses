package de.schillermann.jresponses;

import java.io.IOException;
import java.net.Socket;

public class Main {
  public static void main(String[] args) throws IOException {
    new Front(new Session() {
      @Override
      public void dispatch(Socket socket) throws IOException {
        new ResponseStatus(
            new ResponseHeader(
                new ResponseBody("<h1>Hello World!</h1>"),
                "Content-Type", "text/html"),
            200, "OK")
            .printTo(socket.getOutputStream());
      }
    }, 8080).listen();
  }
}
