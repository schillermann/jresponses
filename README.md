# JResponses

A simple, composable Java web framework built on raw sockets.

Inspired by pure OOP, Alan Kay with [Smalltalk](https://en.wikipedia.org/wiki/Smalltalk), and Yegor Bugayenko's [Cactoos](https://github.com/yegor256/takes), [Takes](https://github.com/yegor256/takes), and [JPages](https://github.com/yegor256/jpages).

- [Quick Start](#quick-start)
- [Request](#request)
  - [Body Decorators](#body-decorators)
- [Routing](#routing)
- [Concurrency & Configuration](#concurrency--configuration)
- [Installation](#installation)
- [Design](#design)
  - [Composition over Conditionals](#composition-over-conditionals)
  - [Why Media?](#why-media)
- [Localization & Encoding](#localization--encoding)

## Quick Start

The simplest way to start a server is to use the default `WireFront` constructor.
By default, it listens on port **8080** and uses a connection pool with as many threads as there are **available processors** on your machine (falling back to **4**).

```java
import de.schillermann.jresponses.*;
import java.io.IOException;

public class MyWebServer {
    public static void main(String[] args) throws IOException {
        new WireFront(
            socket -> {
                new ResponseStatusLineOk(
                    new ResponseHeader(
                        new ResponseBody("<h1>Hello from JResponses!</h1>"),
                        "Content-Type", "text/html"))
                    .media(new WireMedia(new SocketOutput(socket)));
            }
        ).value();
    }
}
```

## Request

To read request headers and the body, you can use the `Request` interface and its decorators.
To use custom configurations from the command line, pass them during assembly:

```java
import de.schillermann.jresponses.*;
import java.io.IOException;

new WireFront(
    socket -> {
        Request request = new RequestFromSocket(socket);
        
        Header agent = request.header("User-Agent");
        String body = new RequestBodyText(request).value();

        new ResponseStatusLineOk(
            new ResponseHeader(
                new ResponseBody(
                    new FormattedText(
                        "<html><body><h1>Your Browser: %s</h1><p>Body: %s</p></body></html>",
                        agent.exists() ? agent.string() : "Unknown",
                        body)),
                "Content-Type", "text/html"))
            .media(new WireMedia(new SocketOutput(socket)));
    },
    new ServerSocketOf(new Port(args)),
    new NumberOfConnections(args)
).value();
```

### Body Decorators

To read the request body in an elegant, declarative way, you can use specialized decorators:

#### Plain Text
Use `RequestBodyText` to get the body as a `String`:

```java
import de.schillermann.jresponses.*;

new WireFront(
    socket -> {
        Request request = new RequestFromSocket(socket);
        String body = new RequestBodyText(request).value();

        new ResponseStatusLineOk(
            new ResponseBody(
                new FormattedText("You sent: %s", body)
            )
        ).media(new WireMedia(new SocketOutput(socket)));
    },
    new ServerSocketOf(new Port(args)),
    new NumberOfConnections(args)
).value();
```

#### JSON
Use `RequestBodyJson` to parse the body as a `JsonObject`:

```java
import de.schillermann.jresponses.*;
import jakarta.json.JsonObject;

new WireFront(
    socket -> {
        Request request = new RequestFromSocket(socket);
        JsonObject json = new RequestBodyJson(request).value();
        String name = json.getString("name");

        new ResponseStatusLineOk(
            new ResponseBody(
                new FormattedText("Hello, %s!", name)
            )
        ).media(new WireMedia(new SocketOutput(socket)));
    },
    new ServerSocketOf(new Port(args)),
    new NumberOfConnections(args)
).value();
```

## Routing

For more complex applications, you can use declarative routing with `ResponseForked` and `ForkPath`.
This approach avoids procedural `if/else` logic:

```java
new WireFront(
    socket -> {
        final Request request = new RequestFromSocket(socket);
        new ResponseForked(
            request,
            new ResponseStatusLineNotFound(new ResponseBody("Page Not Found!")),
            new ForkPath("/", new ResponseStatusLineOk(new ResponseBody("Hello World!"))),
            new ForkPath("/balance", new ResponseStatusLineOk(new ResponseBody("42"))),
            new ForkPath("/id", new ResponseStatusLineOk(new ResponseBody("mario")))
        ).media(new WireMedia(new SocketOutput(socket)));
    },
    new ServerSocketOf(new Port(args)),
    new NumberOfConnections(args)
).value();
```

## Concurrency & Configuration

JResponses supports concurrent request processing via a thread pool.
You can configure the port and the number of simultaneous connections directly from the command line:

```bash
java -jar your-app.jar --port=9090 --connections=20
```

Inside your `main` method, assemble the `WireFront` by passing configured objects:

```java
public static void main(String[] args) throws IOException {
    new WireFront(
        session,
        new ServerSocketOf(new Port(args)),
        new NumberOfConnections(args)
    ).value();
}
```

If you don't provide any arguments, `WireFront` uses these defaults:
- **Port:** 8080
- **Connections:** Available processors (minimum **4**)

```java
new WireFront(session).value();
```

## Installation

Since this library is not yet published to Maven Central, you can install it into your local Maven repository:

```bash
./gradlew publishToMavenLocal
```

## Design

JResponses is designed with composition in mind.
Every part of an HTTP response (Status, Headers, Body) is a decorator that can be combined to build the final response output.

### Composition over Conditionals

Instead of using procedural `if/else` logic in your request handlers, you should build your responses by composing specialized objects. Every component of an HTTP response—status, headers, and body—is an implementation of the `Response` interface.

### Why Media?

In JResponses, you never see a naked `OutputStream`. This is by design.
A raw stream is a data-hungry, procedural monster that forces you to manually format HTTP headers, manage byte arrays, and track stream states.
That's not OOP, it's a script.

Instead, we have `Media` and `WireMedia`.

#### Encapsulation of OutputStream

The `OutputStream` is encapsulated within `WireMedia`.
This ensures that you don't "get" the stream to "write" to it.
You don't ask the media for its internals, that would be a violation of its autonomy.
Instead, the `Response` *acts* upon the `Media`.
It tells the media: "Here is my status code," "Here is a header," and "Here is my body."

The media knows how to format these into the HTTP protocol.
By encapsulating the stream, we maintain **declarative integrity**.
Your response objects don't know *how* to write to a socket, they only know *what* they are.

#### Pushing Procedural Logic to the Margins

In a well-designed object-oriented system, procedural logic—formatting strings, writing to streams, handling byte-level details—must be pushed to the very margins of the codebase.

In JResponses, the "margin" is `WireMedia`.
It's the only place where the raw HTTP protocol is constructed.
The rest of your application remains a clean object-oriented, composable graph of objects.
You build a response by nesting decorators, and only at the very last moment do you represent it through a medium.

This approach keeps your business logic pure and your infrastructure separate.

## Localization & Encoding

The framework uses `Charset.defaultCharset()` throughout the codebase.
If you need to use a specific encoding like `UTF-8`, ensure that your JVM's default charset is set accordingly (e.g., via the `-Dfile.encoding=UTF-8` system property).
