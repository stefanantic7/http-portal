package app.controllers;

import clientportal.Client;
import framework.request.Request;
import framework.response.HtmlResponse;
import framework.response.Response;

import java.util.HashMap;

public class InspireController {

    public Response load(Client client) {

//        try {
//            System.out.println(client.get("https://quotes.rest/qod"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        HashMap<String, String> variables = new HashMap<String, String>();
        variables.put("quote", "Uspeh je loš učitelj. Zavede pametne ljude da misle da ne mogu da izgube");
        variables.put("author", "Bill Gates");
        return new HtmlResponse("quote.html", variables);
    }

}
