package api.helpers;

import java.util.LinkedList;

import play.Logger;
import play.i18n.Lang;
import play.i18n.Messages;

public class PresentationHelper {
	
	public static String stripHTML(String in) {
		return stripHTML(in, false, null);
	}
	
	public static String stripHTML(String in, boolean stripNewlines, Integer maxLength) {
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
		    } else if(stripNewlines && (c == '\n' || c == '\r')) {
		    	continue;
		    }
		    if(!htmlMode) {
		    	result += c;
		    }
		}
		return result;
	}

	/**
	 * 
	 * @param t Difference in milliseconds.
	 * @return
	 */
	public static String timeDifferenceToString(long t) {
		String result = "";
		LinkedList<String> terms = new LinkedList<String>();
		
		int ms = (int) (t % 1000);
		t -= ms;
		t /= 1000;

		int s = (int) (t % 60);
		t -= s;
		t /= 60;

		int m = (int) (t % 60);
		t -= m;
		t /= 60;

		int h = (int) (t % 24);
		t -= h;
		t /= 24;

		int d = (int) t;
		
		if(d == 1) {
			terms.add(Messages.get("date.day", Integer.toString(d)));
		} else if(d > 1) {
			terms.add(Messages.get("date.days", Integer.toString(d)));
		}
		
		if(h == 1) {
			terms.add(Messages.get("date.hour", Integer.toString(h)));
		} else if(h > 1) {
			terms.add(Messages.get("date.hours", Integer.toString(h)));
		}
		
		if(m == 1) {
			terms.add(Messages.get("date.minute", Integer.toString(m)));
		} else if(m > 1) {
			terms.add(Messages.get("date.minutes", Integer.toString(m)));
		}
		
		if(s == 1) {
			terms.add(Messages.get("date.second", Integer.toString(s)));
		} else if(s > 1) {
			terms.add(Messages.get("date.seconds", Integer.toString(s)));
		}
		
		return listing(terms.toArray());
	}
	
	public static String listing(Object[] terms) {
		if(terms.length < 2) {
			if(terms.length == 1) {
				return terms[0].toString();
			} else {
				return "";
			}
		} else { 
			String result = "";
			for(int i = 0; i < terms.length; i++) {
				if(i == terms.length - 1) {
					result += " "+Messages.get("date.and")+" ";
				} else if(i > 0) {
					result += Messages.get("date.comma");
				}
				result += terms[i].toString();
			}
			return result;
		}
	}
}
