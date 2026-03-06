package de.schillermann.jpages;

public final class HelloWorldPage implements Page {

  public HelloWorldPage(Request input) {

  }

  @Override
  public void printTo(Media media) {
    media.withStatus(200, "OK");
    media.withHeader("Content-Type", "text/html");
    media.withBody(new InputStreamOf("<h1>Hello World!</h1>"));
  }
}
