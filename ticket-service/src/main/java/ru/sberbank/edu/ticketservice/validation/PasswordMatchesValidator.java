package ru.sberbank.edu.ticketservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.sberbank.edu.ticketservice.dto.UserDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override 
    public void initialize(final PasswordMatches constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();      
    }
    @Override
    public boolean isValid(final Object object, ConstraintValidatorContext context) {
        final UserDto userDto = (UserDto) object;
        boolean isValid = userDto.getPassword().equals(userDto.getMatchingPassword());
        if (!isValid) {
            context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(secondFieldName)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        }
        return isValid;
    }
}
