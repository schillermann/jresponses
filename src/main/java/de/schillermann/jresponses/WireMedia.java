package de.schillermann.jresponses;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class WireMedia implements Media {
  private final Scalar<OutputStream> output;

  public WireMedia(final Scalar<OutputStream> out) {
    this.output = out;
  }

  @Override
  public Media status(final int code, final String message) throws IOException {
    this.output.value().write(
        String.format("HTTP/1.1 %d %s\r\n", code, message).getBytes());
    return new WireMedia(this.output);
  }

  @Override
  public Media header(final String name, final String value) throws IOException {
    this.output.value().write(
        String.format("%s: %s\r\n", name, value).getBytes());
    return new WireMedia(this.output);
  }

  @Override
  public Media body(final InputStream stream) throws IOException {
    this.output.value().write("\r\n".getBytes());
    stream.transferTo(this.output.value());
    return new WireMedia(this.output);
  }
}
