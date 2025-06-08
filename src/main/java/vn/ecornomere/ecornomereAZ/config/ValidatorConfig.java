package vn.ecornomere.ecornomereAZ.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import vn.ecornomere.ecornomereAZ.config.annotation.checkemail.UniqueEmailValidator;

@Configuration
public class ValidatorConfig {
    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @Bean
    public UniqueEmailValidator uniqueEmailValidator() {
        return new UniqueEmailValidator();
    }

}
