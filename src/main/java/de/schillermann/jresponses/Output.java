package de.schillermann.jresponses;

import java.io.InputStream;

public interface Output {
  void print(String text);

  void receive(InputStream stream);
}
