package api.helpers;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GsonSkip {
	Class<?>[] classes() default {};
	String[] applications() default "";
}
