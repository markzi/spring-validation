package uk.co.markzi.spring_validation.quote.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.markzi.spring_validation.quote.QuoteService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class LongQuoteIdValidator implements ConstraintValidator<ValidateLongQuoteId, Long> {

    private final QuoteService quoteService;

    @Autowired
    public LongQuoteIdValidator(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @Override
    public void initialize(ValidateLongQuoteId validateQuoteId) {

    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {

        boolean valid = true;

        try {
            int noOfQuotes = quoteService.getNoOfQuotes();
            if (id > noOfQuotes) {
                valid = false;
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(String.format("has to be between 1 and %s", noOfQuotes)).addConstraintViolation();
            }

        } catch (NumberFormatException numberFormatException) {
            valid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Has to be an integer").addPropertyNode("id").addConstraintViolation();
        }
        return valid;

    }
}
