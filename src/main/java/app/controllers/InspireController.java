package app.controllers;

import app.models.Quote;
import app.services.QuotesService;
import clientportal.Client;
import framework.inject.specifications.Autowire;
import framework.response.HtmlResponse;
import framework.response.RedirectResponse;
import framework.response.Response;

import java.util.HashMap;

public class InspireController {

    @Autowire
    private QuotesService quotesService;

    public Response load() {
        return new RedirectResponse("/quote");
    }

    public Response random(Client client) {
        Quote quote = this.quotesService.getRandomQuote();
        return getResponse(quote);
    }

    public Response qod() {
        Quote quote = this.quotesService.getQuoteOfDay();
        return getResponse(quote);
    }

    private Response getResponse(Quote quote) {
        HashMap<String, String> variables = new HashMap<String, String>();
        variables.put("quote", quote.getText());
        variables.put("author", quote.getAuthor());
        return new HtmlResponse("quote.html", variables);
    }
}
