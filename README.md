# JResponses

A simple, composable Java web framework built on raw sockets.

Inspired by pure OOP, Alan Kay with [Smalltalk](https://en.wikipedia.org/wiki/Smalltalk), and Yegor Bugayenko's [Cactoos](https://github.com/yegor256/cactoos), [Takes](https://github.com/yegor256/takes), and [JPages](https://github.com/yegor256/jpages).

## Quick Start

```java
import de.schillermann.jresponses.*;
import java.io.IOException;
import java.net.Socket;

public class MyWebServer {
    public static void main(String[] args) throws IOException {
        new Front(socket -> {
            new ResponseStatusLineOk(
                new ResponseHeader(
                    new ResponseBody("<h1>Hello from JResponses!</h1>"),
                    "Content-Type", "text/html"))
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

            new ResponseStatusLineOk(
                new ResponseHeader(
                    new ResponseBody(
                        String.format(
                            "<html><body><h1>Your Browser: %s</h1><p>Body: %s</p></body></html>",
                            agent.exists() ? agent.string() : "Unknown",
                            body)),
                    "Content-Type", "text/html"))
                .printTo(socket.getOutputStream());
        }, 8080).listen();
    }
}
```

## Routing

For more complex applications, you can use declarative routing with `ResponseForked` and `ForkPath`.
This approach avoids procedural `if/else` logic:

```java
new Front(socket -> {
    final Request request = new RequestFromSocket(socket);
    new ResponseForked(
        request,
        new ResponseStatusLineNotFound(new ResponseBody("Page Not Found!")),
        new ForkPath("/", new ResponseStatusLineOk(new ResponseBody("Hello World!"))),
        new ForkPath("/balance", new ResponseStatusLineOk(new ResponseBody("42"))),
        new ForkPath("/id", new ResponseStatusLineOk(new ResponseBody("mario")))
    ).printTo(socket.getOutputStream());
}, 8080).listen();
```

## Composition over Conditionals

Instead of using procedural `if/else` logic in your request handlers, you should build your responses by composing specialized objects. Every component of an HTTP response—status, headers, and body—is an implementation of the `Response` interface.

Here is an example of how you can create custom response components that adapt their behavior based on the user's authentication status:

```java
// Custom status line based on authentication
public final class UserStatusLine implements Response {
    private final Response origin;
    private final boolean authenticated;

    public UserStatusLine(Response origin, boolean authenticated) {
        this.origin = origin;
        this.authenticated = authenticated;
    }

    @Override
    public void printTo(OutputStream out) throws IOException {
        final int code = this.authenticated ? 200 : 401;
        final String msg = this.authenticated ? "OK" : "Unauthorized";
        new ResponseStatusLine(this.origin, code, msg).printTo(out);
    }
}

// Custom header based on user name
public final class UserHeader implements Response {
    private final Response origin;
    private final String name;

    public UserHeader(Response origin, String user) {
        this.origin = origin;
        this.name = user;
    }

    @Override
    public void printTo(OutputStream out) throws IOException {
        if (!this.name.isEmpty()) {
            new ResponseHeader(this.origin, "X-Logged-In-As", this.name).printTo(out);
        } else {
            this.origin.printTo(out);
        }
    }
}

// Custom body based on authentication
public final class UserBody implements Response {
    private final String name;

    public UserBody(String user) {
        this.name = user;
    }

    @Override
    public void printTo(OutputStream out) throws IOException {
        final String message = this.name.isEmpty() 
            ? "Please, log in." 
            : String.format("Welcome, %s!", this.name);
        new ResponseBody(message).printTo(out);
    }
}
```

Then you can use them together to build a complete response:

```java
final String user = "mario"; // or empty if not logged in
final boolean authenticated = !user.isEmpty();

new UserStatusLine(
    new UserHeader(
        new UserBody(user),
        user
    ),
    authenticated
).printTo(socket.getOutputStream());
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
