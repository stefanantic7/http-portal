package app.controllers;

import clientportal.Client;
import framework.request.Request;
import framework.response.Response;

public class InspireController {

    public Response load(Client client) {

        try {
            System.out.println(client.get("https://quotes.rest/qod"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Response();
    }

}
