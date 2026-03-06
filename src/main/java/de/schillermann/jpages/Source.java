package de.schillermann.jpages;

import java.io.IOException;

public interface Source<T> {
  T value() throws IOException;
}
