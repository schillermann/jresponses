# JResponses

A simple, composable Java web framework built on raw sockets.

## Quick Start

```java
import de.schillermann.jresponses.*;
import java.io.IOException;
import java.net.Socket;

public class MyWebServer {
    public static void main(String[] args) throws IOException {
        new Front(socket -> {
            new ResponseStatus(
                new ResponseHeader(
                    new ResponseBody("<h1>Hello from JPages!</h1>"),
                    "Content-Type", "text/html"),
                200, "OK")
                .printTo(socket.getOutputStream());
        }, 8080).listen();
    }
}
```

## Handling Requests

To read request headers and the body, you can use the `Request` interface:

```java
import de.schillermann.jresponses.*;
import java.io.IOException;
import java.net.Socket;

public class MyWebServer {
    public static void main(String[] args) throws IOException {
        new Front(socket -> {
            Request request = new RequestFromSocket(socket);
            
            Header agent = request.header("User-Agent");
            String body = new String(request.body().readAllBytes());

            new ResponseStatus(
                new ResponseHeader(
                    new ResponseBody(
                        String.format(
                            "<html><body><h1>Your Browser: %s</h1><p>Body: %s</p></body></html>",
                            agent.exists() ? agent.string() : "Unknown",
                            body)),
                    "Content-Type", "text/html"),
                200, "OK")
                .printTo(socket.getOutputStream());
        }, 8080).listen();
    }
}
```

## Installation

Since this library is not yet published to Maven Central, you can install it into your local Maven repository:

```bash
./gradlew publishToMavenLocal
```

Then, in your other Java project's `build.gradle`, add `mavenLocal()` to your repositories and include the dependency:

```gradle
repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation 'de.schillermann.jresponses:jresponses:0.1.0'
}
```

## Design

JResponses is designed with composition in mind.
Every part of an HTTP response (Status, Headers, Body) is a decorator that can be combined to build the final response output.
