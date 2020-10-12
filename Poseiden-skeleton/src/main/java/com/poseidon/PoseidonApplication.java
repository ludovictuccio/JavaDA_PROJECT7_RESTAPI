package com.poseidon;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEncryptableProperties
@EnableSwagger2
public class PoseidonApplication {

    public PoseidonApplication() {
    }

    public static void main(final String[] args) {
        SpringApplication.run(PoseidonApplication.class, args);
    }

    /**
     * Method used to configure httptrace endpoint with actuator.
     */
    @Bean
    public HttpTraceRepository htttpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}
