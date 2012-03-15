package models.helpers;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GsonSkip {

	Class<?> value();

}
