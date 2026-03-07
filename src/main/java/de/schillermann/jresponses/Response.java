package de.schillermann.jresponses;

import java.io.IOException;
import java.io.OutputStream;

public interface Response {
  void printTo(OutputStream out) throws IOException;
}
