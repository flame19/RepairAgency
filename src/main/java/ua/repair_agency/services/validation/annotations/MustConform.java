package ua.repair_agency.services.validation.annotations;

import ua.repair_agency.services.validation.regex.Regex;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MustConform {
    Regex value() default Regex.EMPTY;
}
