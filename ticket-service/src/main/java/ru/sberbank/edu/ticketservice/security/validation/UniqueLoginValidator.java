package ru.sberbank.edu.ticketservice.security.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.sberbank.edu.ticketservice.profile.service.UserService;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, Object>{

    private UserService userService;
    private String message;
    
    public UniqueLoginValidator(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public void initialize(final UniqueLogin constraintAnnotation) {
        this.message = constraintAnnotation.message();       
    }
    
    
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        String login = (String) object;
        boolean isValid = true;
        if (userService.loginExists(login)) {
            isValid = false;
        }
        if (!isValid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return isValid;
    }

}
