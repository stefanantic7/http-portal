package clientportal;

import com.google.gson.JsonObject;

import java.util.HashMap;

public interface Client {

    public JsonObject get(String url)
            throws Exception;

    public JsonObject post(String url)
            throws Exception;

    public JsonObject get(String url, HashMap<String, String> parameters)
            throws Exception;

    public JsonObject post(String url, HashMap<String, String> parameters)
            throws Exception;

    public JsonObject get(String url, HashMap<String, String> parameters, HashMap<String, String> headers)
            throws Exception;

    public JsonObject post(String url, HashMap<String, String> parameters, HashMap<String, String> headers)
            throws Exception;

}
