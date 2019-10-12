package app.services;

import app.models.Quote;
import app.repositories.QuotesRepositoryInterface;
import clientportal.Client;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import framework.inject.specifications.Autowire;
import framework.inject.specifications.Service;

import java.util.Random;

@Service
public class QuotesService {

    @Autowire
    private QuotesRepositoryInterface quotesRepository;

    @Autowire
    private Client client;

    public Quote getRandomQuote()
    {
        return this.quotesRepository.getRandomQuote();
    }

    public Quote getQuoteOfDay()
    {
        Quote quote = null;
        try {
            JsonArray quotesArray = this.client.get("https://quotes.rest/qod")
                    .getAsJsonObject("contents")
                    .getAsJsonArray("quotes");
            Random random = new Random();
            JsonObject randomQuote = quotesArray.get(random.nextInt(quotesArray.size())).getAsJsonObject();
            quote = new Quote(randomQuote.get("quote").getAsString(), randomQuote.get("author").getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(quote==null) {
            quote = this.quotesRepository.getRandomQuote();
        }

        return quote;
    }
}
