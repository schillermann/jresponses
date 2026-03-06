package de.schillermann.jpages;

import java.io.InputStream;

public interface Output {
  void print(String text);

  void receive(InputStream stream);
}
