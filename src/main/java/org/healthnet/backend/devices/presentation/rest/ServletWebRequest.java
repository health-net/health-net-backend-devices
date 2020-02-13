package org.healthnet.backend.devices.presentation.rest;

import javax.servlet.http.HttpServletRequest;

public class ServletWebRequest extends WebRequest {
    private final HttpServletRequest httpServletRequest;

    public ServletWebRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean isOn(String path) {
        String pathInfo = (httpServletRequest.getPathInfo() == null) ? "" : httpServletRequest.getPathInfo();
        return pathInfo.matches(path);
    }

    @Override
    public boolean isGet() {
        return httpServletRequest.getMethod().equals("GET");
    }

    @Override
    public String getPathVariable(int index) {
        return httpServletRequest.getPathInfo().split("/")[index + 1];
    }

    @Override
    public String getQueryParameter(String name) {
        return httpServletRequest.getParameterMap().get(name)[0];
    }
}
