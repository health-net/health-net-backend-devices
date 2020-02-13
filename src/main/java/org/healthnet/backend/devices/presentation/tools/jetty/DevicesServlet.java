package org.healthnet.backend.devices.presentation.tools.jetty;

import org.healthnet.backend.devices.presentation.rest.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DevicesServlet extends HttpServlet {
    private final WebHandler singleDeviceWebHandler;
    private final WebHandler groupDeviceByPatientWebHandler;
    private final WebResponseFactory webResponseFactory;

    public DevicesServlet(WebHandler singleDeviceWebHandler, WebHandler groupDeviceByPatientWebHandler, WebResponseFactory webResponseFactory) {
        this.singleDeviceWebHandler = singleDeviceWebHandler;
        this.groupDeviceByPatientWebHandler = groupDeviceByPatientWebHandler;
        this.webResponseFactory = webResponseFactory;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebRequest webRequest = new ServletWebRequest(req);
        WebResponse webResponse = route(webRequest);
        resp.setStatus(webResponse.getStatusCode());
        resp.setContentType("application/json");
        resp.getWriter().write(webResponse.getBodyAsString());
    }

    private WebResponse route(WebRequest webRequest) {
        if (webRequest.isGet() && webRequest.isOn("(/?)")) {
            return groupDeviceByPatientWebHandler.handle(webRequest);
        } else if (webRequest.isGet() && webRequest.isOn("(.+)(/?)$")) {
            return singleDeviceWebHandler.handle(webRequest);
        } else {
            return webResponseFactory.createNotFound("Route not found");
        }
    }
}
