package org.openjfx.validators;



import com.jfoenix.validation.RegexValidator;

import java.awt.*;
import java.util.regex.Pattern;

public class IDValidator extends TextValidator {
    private static final String HEXADECIMAL_ID_PATTERN = "^(?:[0-9A-Fa-f]{3})$";
    private static final String VALIDATOR_MESSAGE = "Please enter a 4 digit hexadecimal number";
    private IDValidator(){}
    private static final RegexValidator validator = new RegexValidator(VALIDATOR_MESSAGE);
    static{
       validator.setRegexPattern(HEXADECIMAL_ID_PATTERN);
    }
    public static RegexValidator getValidator() {
        return validator;
    }
}
