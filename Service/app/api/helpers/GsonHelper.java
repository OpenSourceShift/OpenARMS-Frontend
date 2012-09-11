package api.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import play.Logger;
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

	@SuppressWarnings("unchecked")
	public static <C> C fromJson(InputStream is, Class<? extends Object> c) throws Exception {
		if(c == null) {
			throw new Exception("Second argument Class c, cannot be null.");
		} else {
			String json = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				json += line;
			}
			br.close();
			Logger.debug("GsonHelper.fromJson() reads '%s' as a %s", json, c.getCanonicalName());
			
			Gson g = get(c.getClass());
			Object o = g.fromJson(json, c);
			if(o == null || c.isInstance(o)) {
				return (C) o;
			} else {
				throw new Exception("The Gson object is not of the expected type, got "+o.getClass().getCanonicalName()+" expected "+c.getCanonicalName());
			}
		}
	}
}
