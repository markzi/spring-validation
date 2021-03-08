package uk.co.markzi.spring_validation.quote.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.markzi.spring_validation.quote.Quote;
import uk.co.markzi.spring_validation.quote.QuoteRequest;
import uk.co.markzi.spring_validation.quote.QuoteService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
public class QuoteRequestValidator implements ConstraintValidator<ValidateQuoteRequest, QuoteRequest> {

    private final QuoteService quoteService;

    @Autowired
    public QuoteRequestValidator(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @Override
    public void initialize(ValidateQuoteRequest validateQuoteRequest) {

    }

    @Override
    public boolean isValid(QuoteRequest quoteRequest, ConstraintValidatorContext context) {

        boolean valid = true;

        Optional<Quote> quote = quoteService.getAllQuotes().stream().filter(foundQuote -> foundQuote.getQuote().equals(quoteRequest.getQuote())).findFirst();
        if(quote.isPresent()) {
            valid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("quote already exists")).addPropertyNode("quote").addConstraintViolation();
        }

        return valid;

    }
}
