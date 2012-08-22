package api.helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.reflect.TypeToken;

public class AnnotationExclusionStrategy implements ExclusionStrategy {
	
	private Class<?> clazz;
	private String application;
	
	public AnnotationExclusionStrategy(Class<?> clazz, String application) {
		this.clazz = clazz;
		this.application = application;
	}
	
	public boolean shouldSkipClass(Class<?> c) {
		for(Annotation a: c.getAnnotations()) {
			if(a instanceof GsonSkip) {
				GsonSkip gs = (GsonSkip) a;
				if(gs.classes().length == 0) {
					// No classes was selected, then its any class.
					return true;
				} else {
					for(Class<?> someClass: gs.classes()) {
						if(someClass.equals(clazz)) {
							/*
							for(String someApplication: gs.applications()) {
								if(someApplication.equals(this.application)) {
									return true;
								}
							}
							*/
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean shouldSkipField(FieldAttributes fa) {
		for(Annotation a: fa.getAnnotations()) {
			if(a instanceof GsonSkip) {
				GsonSkip gs = (GsonSkip) a;
				for(Class<?> someClass: gs.classes()) {
					if(someClass.equals(clazz)) {
						for(String someApplication: gs.applications()) {
							if(someApplication.equals(this.application)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

}
