package com.duoc.AlphaSquad.Validacion;

import com.duoc.AlphaSquad.Validacion.RutValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RutValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RutValido {

    String message() default "RUT inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
