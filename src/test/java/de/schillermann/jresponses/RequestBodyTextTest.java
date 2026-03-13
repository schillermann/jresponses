package de.schillermann.jresponses;

import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test case for {@link RequestBodyText}.
 */
final class RequestBodyTextTest {

  @Test
  void readsTextBody() throws IOException {
    final String data = "POST / HTTP/1.1\r\nContent-Length: 5\r\n\r\nHello";
    final Request request = new RequestFromStream(new FakeCursor(data));
    final String text = new RequestBodyText(request).value();
    assertEquals("Hello", text, "The text body should be 'Hello'");
  }
}
