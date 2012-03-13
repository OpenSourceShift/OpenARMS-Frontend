package models;

import java.lang.reflect.Type;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.reflect.TypeToken;

public class SelfExclusionStrategy implements ExclusionStrategy {

	private Type t;
	public SelfExclusionStrategy(Class<?> c) {
		t = TypeToken.get(c).getType();
	}
	
	@Override
	public boolean shouldSkipClass(Class<?> c) {
		return false;
	}

	@Override
	public boolean shouldSkipField(FieldAttributes fa) {
		if(fa.getDeclaredType().equals(t)) {
			return true;
		}
		return false;
	}

}
