package uk.co.markzi.spring_validation.quote;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private final Map<Long, Quote> quotes = new HashMap<>();

    public QuoteService() {
        quotes.put(Long.valueOf(1), new Quote("The greatest glory in living lies not in never falling, but in rising every time we fall.", "Nelson Mandela"));
        quotes.put(Long.valueOf(2), new Quote("The way to get started is to quit talking and begin doing.", "Walt Disney"));
        quotes.put(Long.valueOf(3), new Quote("Your time is limited, so don't waste it living someone else's life. Don't be trapped by dogma – which is living with the results of other people's thinking.", "Steve Jobs"));
        quotes.put(Long.valueOf(4), new Quote("If life were predictable it would cease to be life, and be without flavor.", "Eleanor Roosevelt"));
        quotes.put(Long.valueOf(5), new Quote("If you look at what you have in life, you'll always have more. If you look at what you don't have in life, you'll never have enough.", "Oprah Winfrey"));
        quotes.put(Long.valueOf(6), new Quote("If you set your goals ridiculously high and it's a failure, you will fail above everyone else's success.", "James Cameron"));
        quotes.put(Long.valueOf(7), new Quote("Life is what happens when you're busy making other plans.", "John Lennon"));
    }

    public List<Quote> getAllQuotes() {
        return quotes.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public Quote getRandomQuote() {
        return quotes.get(ThreadLocalRandom.current().nextInt(quotes.size()));
    }

    public Quote getRandomQuote(Long id) {
        return quotes.get(id);
    }

    public int getNoOfQuotes() {
        return quotes.size();
    }

    public Quote save(QuoteRequest quoteRequest) {
        Long index = Long.valueOf(getNoOfQuotes() + 1);
        Quote quote = new Quote(quoteRequest.getQuote(), quoteRequest.getBy());
        quotes.put(index, quote);
        return quote;
    }
}
