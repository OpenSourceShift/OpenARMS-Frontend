package api.helpers;

public class PresentationHelper {
	public static String stripHTML(String in) {
		return stripHTML(in, false, null);
	}
	public static String stripHTML(String in, boolean andNewlines, Integer maxLength) {
		String result = "";
		boolean htmlMode = false;
		int length = in.length();
		if(maxLength != null && length > maxLength) {
			length = maxLength.intValue();
		}
		for (int i = 0; i < length; i++){
		    char c = in.charAt(i);
		    if(c == '<') {
		    	htmlMode = true;
		    } else if(c == '>') {
		    	htmlMode = false;
		    	continue;
		    } else if(andNewlines && (c == '\n' || c == '\r')) {
		    	continue;
		    }
		    if(!htmlMode) {
		    	result += c;
		    }
		}
		return result;
	}
}
