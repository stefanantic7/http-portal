package clientportal;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

public class ClientPortalAdapter implements Client {

    private ClientPortal clientPortal;

    public ClientPortalAdapter() {
        this.clientPortal = new ClientPortal();
    }

    public JsonObject get(String url) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject object;
        this.clientPortal.addRequestProperty("Accept", "application/json");
        object = parser.parse(this.clientPortal.get(url)).getAsJsonObject();

        return object;
    }

    public JsonObject post(String url) throws Exception {
        throw new Exception("Not implemented");
    }

    public JsonObject get(String url, HashMap<String, String> parameters) throws Exception {
        throw new Exception("Not implemented");
    }

    public JsonObject post(String url, HashMap<String, String> parameters) throws Exception {
        throw new Exception("Not implemented");
    }

    public JsonObject get(String url, HashMap<String, String> parameters, HashMap<String, String> headers) throws Exception {
        throw new Exception("Not implemented");
    }

    public JsonObject post(String url, HashMap<String, String> parameters, HashMap<String, String> headers) throws Exception {
        throw new Exception("Not implemented");
    }

}
