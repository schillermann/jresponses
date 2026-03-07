package de.schillermann.jresponses;

import java.io.InputStream;

public interface Media {
  void withHeader(String name, String value);

  void withStatus(int code, String message);

  void withBody(InputStream stream);
}
