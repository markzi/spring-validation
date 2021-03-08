package uk.co.markzi.spring_validation.quote;

import com.fasterxml.jackson.annotation.JsonProperty;
import uk.co.markzi.spring_validation.quote.validators.ValidateQuoteRequest;
import uk.co.markzi.spring_validation.validation.groups.ClassValidation;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

@ValidateQuoteRequest(groups = {ClassValidation.class})
@GroupSequence({QuoteRequest.class, ClassValidation.class})
public class QuoteRequest {

    @NotNull
    private final String quote;

    @NotNull
    private final String by;

    public QuoteRequest(
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
