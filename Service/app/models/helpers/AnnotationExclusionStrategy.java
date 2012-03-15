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
		this.clazz = clazz;
	}
	
	public boolean shouldSkipClass(Class<?> c) {
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
