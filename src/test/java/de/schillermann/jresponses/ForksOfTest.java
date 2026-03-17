package de.schillermann.jresponses;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ForksOfTest {

  @Test
  void testForksOfRouting() throws IOException {
    final Request request = new Request() {
        @Override public RequestLine requestLine() { 
            return new RequestLine() {
                @Override public Method method() { return null; }
                @Override public Path path() { return () -> "/test"; }
                @Override public Query query() { return null; }
                @Override public Protocol protocol() { return null; }
            };
        }
        @Override public Header header(String name) { return null; }
        @Override public InputStream body() { return null; }
    };

    final Response fallback = (out) -> out.write("Fallback".getBytes());
    final Response match = (out) -> out.write("Match".getBytes());

    final Forks forks = new ForksOf(Arrays.asList(
        new ForkPath("/not-test", (out) -> out.write("No".getBytes())),
        new ForkPath("/test", match)
    ));

    final Response res = forks.response(request, fallback);
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    res.printTo(out);

    assertEquals("Match", out.toString());
  }

  @Test
  void testForksOfFallback() throws IOException {
    final Request request = new Request() {
        @Override public RequestLine requestLine() { 
            return new RequestLine() {
                @Override public Method method() { return null; }
                @Override public Path path() { return () -> "/unknown"; }
                @Override public Query query() { return null; }
                @Override public Protocol protocol() { return null; }
            };
        }
        @Override public Header header(String name) { return null; }
        @Override public InputStream body() { return null; }
    };

    final Response fallback = (out) -> out.write("Fallback".getBytes());

    final Forks forks = new ForksOf(Arrays.asList(
        new ForkPath("/not-test", (out) -> out.write("No".getBytes()))
    ));

    final Response res = forks.response(request, fallback);
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    res.printTo(out);

    assertEquals("Fallback", out.toString());
  }

  @Test
  void testNoForks() throws IOException {
    final Request request = null; // NoRequest not needed if we don't access it
    final Response fallback = (out) -> out.write("Fallback".getBytes());

    final Forks forks = new ForksOf(Collections.emptyList());

    final Response res = forks.response(request, fallback);
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    res.printTo(out);

    assertEquals("Fallback", out.toString());
  }
}
