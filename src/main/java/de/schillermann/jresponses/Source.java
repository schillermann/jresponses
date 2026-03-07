package de.schillermann.jresponses;

import java.io.IOException;

public interface Source<T> {
  T value() throws IOException;
}
