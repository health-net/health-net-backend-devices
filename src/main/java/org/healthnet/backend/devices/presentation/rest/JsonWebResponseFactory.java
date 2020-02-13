package org.healthnet.backend.devices.presentation.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class JsonWebResponseFactory extends WebResponseFactory {
    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public WebResponse createOk(Object body) {
        return new WebResponse(200, gson.toJson(body));
    }

    @Override
    public WebResponse createNotFound(String message) {
        return new WebResponse(404, gson.toJson(createError(message)));
    }

    @Override
    public WebResponse createInternalServerError(String message) {
        return new WebResponse(500, gson.toJson(createError(message)));
    }

    private JsonObject createError(String message) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", message);
        return jsonObject;
    }
}
