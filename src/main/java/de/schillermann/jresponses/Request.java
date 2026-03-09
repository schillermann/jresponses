package de.schillermann.jresponses;

import java.io.InputStream;

public interface Request {
  RequestLine requestLine();

  Header header(String name);

  InputStream body();
}
