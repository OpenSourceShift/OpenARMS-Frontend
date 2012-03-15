package models.helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.reflect.TypeToken;

public class AnnotationExclusionStrategy implements ExclusionStrategy {
	
	private Class<?> clazz;
	
	public AnnotationExclusionStrategy(Class<?> clazz) {
		System.out.println("Creating an AnnotationExclusionStrategy for "+clazz);
		this.clazz = clazz;
	}
	
	public boolean shouldSkipClass(Class<?> c) {
		System.out.println("!!! shouldSkipClass called with "+c.toString());
		Annotation a = c.getAnnotation(GsonSkip.class);
		if(a instanceof GsonSkip) {
			GsonSkip gs = (GsonSkip) a;
			if(gs.value() != null && gs.value().equals(clazz)) {
				return true;
			} else if (gs.value() != null) {
				return true;
			}
		}
		return false;
	}

	public boolean shouldSkipField(FieldAttributes fa) {
		System.out.println("shouldSkipField called with "+fa.getName()+" of class "+fa.getDeclaredClass().getName());
		Annotation a = fa.getAnnotation(GsonSkip.class);
		if(a instanceof GsonSkip) {
			GsonSkip gs = (GsonSkip) a;
			if(gs.value() != null && clazz.equals(gs.value())) {
				return true;
			} else if (gs.value() != null) {
				return true;
			}
		}
		return false;
	}

}
