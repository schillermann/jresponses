package de.schillermann.jpages;

import java.io.InputStream;

public interface Request {
  Header header(String name);

  InputStream body();
}
