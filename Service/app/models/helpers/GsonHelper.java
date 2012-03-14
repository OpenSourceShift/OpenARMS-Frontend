package models.helpers;

import java.util.HashMap;
import java.util.Map;

import models.Poll;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {
	
	public static Map<Class<?>,GsonBuilder> builders = new HashMap<Class<?>,GsonBuilder>();
	
	public static Gson get(Class<?> clazz) {
		System.out.println("Getting a builder for class: "+clazz.toString());
		GsonBuilder builder = builders.get(clazz);
		if(builder == null) {
			System.out.println("Creating a new builder for "+clazz.toString());
			ExclusionStrategy es = new AnnotationExclusionStrategy(clazz);
			builder = new GsonBuilder();
			builder.addSerializationExclusionStrategy(es);
			// Create it.
			builders.put(clazz, builder);
		}
		return builder.create();
	}
}
