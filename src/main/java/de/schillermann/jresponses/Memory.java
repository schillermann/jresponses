package de.schillermann.jresponses;

import java.io.IOException;

public interface Memory {
  void remember(int data) throws IOException;

  int recall(int position) throws IOException;

  int size();
}
