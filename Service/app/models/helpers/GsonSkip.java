package models.helpers;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GsonSkip {
	public static enum Component {
		Service,
		Frontend
	}
	Class<?> clazz();
	String application();

}
