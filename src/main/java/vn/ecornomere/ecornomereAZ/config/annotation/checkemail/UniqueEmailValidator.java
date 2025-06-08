package vn.ecornomere.ecornomereAZ.config.annotation.checkemail;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.ecornomere.ecornomereAZ.service.UserService;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true; // @NotBlank sẽ xử lý trường hợp null
        }
        return !userService.existsByEmail(email);
    }

}
