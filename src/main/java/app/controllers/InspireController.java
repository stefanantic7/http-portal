package app.controllers;

import app.models.Quote;
import app.repositories.QuotesRepository;
import clientportal.Client;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import framework.request.Request;
import framework.response.HtmlResponse;
import framework.response.RedirectResponse;
import framework.response.Response;

import java.util.HashMap;
import java.util.Random;

public class InspireController {

    public Response load() {
        return new RedirectResponse("/quote");
    }

    public Response quote() {

        QuotesRepository quotesRepository = new QuotesRepository();
        Quote quote = quotesRepository.getRandomQuote();

        return getResponse(quote);
    }

    public Response random(Client client) {
        Quote quote = null;
        try {
            JsonArray quotesArray = client.get("https://quotes.rest/qod")
                    .getAsJsonObject("contents")
                    .getAsJsonArray("quotes");
            Random random = new Random();
            JsonObject randomQuote = quotesArray.get(random.nextInt(quotesArray.size())).getAsJsonObject();
            quote = new Quote(randomQuote.get("quote").getAsString(), randomQuote.get("author").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(quote==null) {
            quote = new QuotesRepository().getRandomQuote();
        }

        return getResponse(quote);

    }

    private Response getResponse(Quote quote) {
        HashMap<String, String> variables = new HashMap<String, String>();
        variables.put("quote", quote.getText());
        variables.put("author", quote.getAuthor());
        return new HtmlResponse("quote.html", variables);
    }
}
