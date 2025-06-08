package vn.ecornomere.ecornomereAZ.config.annotation.checkpassword;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;

import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = PasswordMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatch {
    String message() default "Xác nhận mật khẩu không khớp";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
