package org.healthnet.backend.devices.presentation.tools.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;

public final class JettyEmbeddedServer {
    private final Server server;

    public JettyEmbeddedServer(int port, ContextHandler contextHandler) {
        server = new Server(port);
        server.setHandler(contextHandler);
    }

    public void start() throws Exception {
        server.start();
    }
}
