package models.helpers;

import java.util.HashMap;
import java.util.Map;

import play.Play;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {
	
	public static Map<Class<?>,GsonBuilder> builders = new HashMap<Class<?>,GsonBuilder>();
	
	private static Gson get(Class<?> clazz) {
		GsonBuilder builder = builders.get(clazz);
		if(builder == null) {
			ExclusionStrategy es = new AnnotationExclusionStrategy(clazz, Play.configuration.getProperty("application.name"));
			builder = new GsonBuilder();
			builder.addSerializationExclusionStrategy(es);
			// Create it.
			builders.put(clazz, builder);
		}
		return builder.create();
	}
	
	public static String toJson(Object o) {
		return get(o.getClass()).toJson(o);
	}
	
	public static <C> C fromJson(String i, Class<C> c) {
		return get(c.getClass()).fromJson(i, c);
	}
}
