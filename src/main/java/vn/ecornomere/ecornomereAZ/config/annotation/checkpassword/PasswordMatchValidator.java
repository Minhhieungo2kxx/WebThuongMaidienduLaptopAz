package vn.ecornomere.ecornomereAZ.config.annotation.checkpassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.ecornomere.ecornomereAZ.model.dto.RegisterDTO;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, RegisterDTO> {

    @Override
    public boolean isValid(RegisterDTO dto, ConstraintValidatorContext context) {
        if (dto.getPassword() == null || dto.getConfirmpassword() == null) {
            System.out.println("Validating password match: " + dto.getPassword() + " vs " + dto.getConfirmpassword());
            return true; // @NotBlank sẽ xử lý trường hợp null
        }
        boolean isValid = dto.getPassword().equals(dto.getConfirmpassword());
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("confirmpassword")
                    .addConstraintViolation();
        }
        System.out.println("Validating password match: " + dto.getPassword() + " vs " + dto.getConfirmpassword());
        return isValid;

    }

}
