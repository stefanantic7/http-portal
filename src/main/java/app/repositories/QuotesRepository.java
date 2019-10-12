package app.repositories;

import app.models.Quote;

import java.util.Random;

public class QuotesRepository implements QuotesRepositoryInterface {

    private static Quote[] quotes = {
            new Quote("If today were the last of your life, would you do what you were going to do today?",
                    "Steve Jobs"),
            new Quote("If you are born poor it’s not your mistake, but if you die poor it’s your mistake.",
                    "Bill Gates"),
            new Quote("I don’t care that they stole my idea …  I care that they don’t have any of their own.",
                    "Nikola Tesla"),
            new Quote("We were all born to die once, honor and shame will live forever!",
                    "Petar Petrović Njegoš"),
            new Quote("Do not waste your time on preparing your bed, the bed won't run away, your time will.",
                    "Stefan Antić")

    };

    public Quote getRandomQuote() {
        Random random = new Random();
        return quotes[random.nextInt(quotes.length)];
    }
}
