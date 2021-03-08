package uk.co.markzi.spring_validation.quote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uk.co.markzi.spring_validation.quote.validators.ValidateLongQuoteId;

import javax.validation.Valid;

@RestController
@RequestMapping("/quote")
@Validated
public class QuoteController {

    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Quote get() {
        return quoteService.getRandomQuote();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Quote get(@Valid @ValidateLongQuoteId @PathVariable("id") Long id) {
        return quoteService.getRandomQuote(id);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Quote getByPost(@Valid @RequestBody QuoteRequest quoteRequest) {
        return quoteService.save(quoteRequest);
    }

}
