package io.pivotal.activities.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class Http {
    public static JsonNode get(String url) throws IOException {
        String response = Request.Get(url).execute().returnContent().asString();
        return Json.parse(response);
    }
}
