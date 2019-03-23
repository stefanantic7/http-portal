package clientportal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * A Simple client http request caller.
 */
public class ClientPortal {

    private HashMap<String, String> requestProperties;

    public ClientPortal() {
        this.requestProperties = new HashMap<String, String>();
    }

    public void addRequestProperty(String name, String value) {
        this.requestProperties.put(name, value);
    }

    public String get(String url) throws IOException {
        return this.request("GET", url);
    }

    private String request(String method, String url) throws IOException{
        URL urlForGetRequest = new URL(url);
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();

        for (String propertyName : requestProperties.keySet()) {
            connection.setRequestProperty(propertyName, requestProperties.get(propertyName));
        }

        connection.setRequestMethod(method);
        System.out.println(connection.getHeaderField("Content-Type"));
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        while ((readLine = in .readLine()) != null) {
            response.append(readLine);
        }
        in .close();

        return response.toString();
    }


}
