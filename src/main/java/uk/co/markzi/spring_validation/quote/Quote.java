package uk.co.markzi.spring_validation.quote;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Quote {

    private final String quote;
    private final String by;

    public Quote(
            @JsonProperty("quote") final String quote,
            @JsonProperty("by") String by) {
        this.quote = quote;
        this.by = by;
    }

    public String getQuote() {
        return quote;
    }

    public String getBy() {
        return by;
    }
}
