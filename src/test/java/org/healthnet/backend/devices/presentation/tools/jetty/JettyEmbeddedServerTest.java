package org.healthnet.backend.devices.presentation.tools.jetty;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JettyEmbeddedServerTest {
    private static final int SERVER_PORT = 8080;
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final HttpServlet servlet = new TestServlet();
    private static final ServletContextHandler servletContextHandler = new ServletContextHandler();

    static {
        servletContextHandler.addServlet(new ServletHolder(servlet), "/");
    }

    private static final JettyEmbeddedServer server = new JettyEmbeddedServer(SERVER_PORT, servletContextHandler);

    @Test
    void Invoke_Root_ResponseHasBeenReturned() throws Exception {
        String body = get("/");
        assertEquals("Hello!", body);
    }

    @Test
    void Invoke_ArbitraryPath_ResponseHasBeenReturned() throws Exception {
        String body = get("/foo");
        assertEquals("Hello!", body);
    }

    private String get(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + SERVER_PORT + path))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    @BeforeAll
    static void setUp() throws Exception {
        server.start();
    }

    @AfterAll
    static void tearDown() throws Exception {
        server.stop();
    }

    public static class TestServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.getWriter().write("Hello!");
        }
    }
}
