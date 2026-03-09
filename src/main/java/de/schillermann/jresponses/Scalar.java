package de.schillermann.jresponses;

import java.io.IOException;

public interface Scalar<T> {
  T value() throws IOException;
}
