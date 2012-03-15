package models.helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import models.helpers.GsonSkip.Component;

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
				if(gs.clazz() != null && gs.clazz().equals(clazz)) {
					if(gs.application() != null && gs.application().equals(application)) {
						return true;
					} else if(gs.application() == null) {
						return true;
					}
				} else if (gs.clazz() == null) {
					if(gs.application() != null && gs.application().equals(application)) {
						return true;
					} else if(gs.application() == null) {
						return true;
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
				if(gs.clazz() != null && gs.clazz().equals(clazz)) {
					if(gs.application() != null && gs.application().equals(application)) {
						return true;
					} else if(gs.application() == null) {
						return true;
					}
				} else if (gs.clazz() == null) {
					if(gs.application() != null && gs.application().equals(application)) {
						return true;
					} else if(gs.application() == null) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
