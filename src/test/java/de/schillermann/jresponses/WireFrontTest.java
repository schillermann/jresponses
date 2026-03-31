package de.schillermann.jresponses;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

final class WireFrontTest {

    @Test
    void terminatesOnInterrupt() throws IOException, InterruptedException {
        final ServerSocket socket = mock(ServerSocket.class);
        final Session session = mock(Session.class);
        
        when(socket.accept()).thenAnswer(inv -> {
            try {
                while (true) {
                    Thread.sleep(100);
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            throw new SocketException("Interrupted");
        });

        final Front front = new WireFront(
            session,
            () -> socket,
            () -> 1
        );

        final AtomicBoolean finished = new AtomicBoolean(false);
        final Thread thread = new Thread(() -> {
            try {
                front.conclusion();
                finished.set(true);
            } catch (IOException e) {
                // Ignore
            }
        });

        thread.start();
        Thread.sleep(100);
        thread.interrupt();
        thread.join(1000);

        assertTrue(finished.get(), "Front should have finished after interruption");
    }
}
