package org.healthnet.backend.devices.presentation.tools.jetty;

import org.healthnet.backend.devices.presentation.rest.WebHandler;
import org.healthnet.backend.devices.presentation.rest.WebRequest;
import org.healthnet.backend.devices.presentation.rest.WebResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DevicesServlet extends HttpServlet {
    private final WebHandler router;

    public DevicesServlet(WebHandler router) {
        this.router = router;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = (req.getPathInfo() == null) ? "" : req.getPathInfo();
        WebRequest webRequest = new WebRequest(req.getMethod(), path, req.getReader());
        WebResponse webResponse = router.handle(webRequest);
        resp.setContentType("application/json");
        resp.setStatus(webResponse.getStatusCode());
        resp.getWriter().write(webResponse.getBodyContent());
    }
}
