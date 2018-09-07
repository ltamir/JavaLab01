package il.ac.ariel.liortamir.javalab.bl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * A container class holding the data of a request or its response.<br>
 * The class contains getters and setters for int, double, String and {@link java.time.LocalDateTime}.<br>
 * 
 * The key for each field must come from {@link il.ac.ariel.liortamir.javalab.api.API}
 * @author liort
 *
 */
public class EventData {

	private Map<String, Object> dataMap = new HashMap<>();
	
	// ***** getters and setters for data ***** //
	
	public void set(String tag, int value) {
		dataMap.put(tag, value);
	}
	
	public void set(String tag, double value) {
		dataMap.put(tag, value);
	}
	
	public void set(String tag, String value) {
		dataMap.put(tag, value);
	}
	
	public void set(String tag, LocalDateTime value) {
		dataMap.put(tag, value);
	}
	
	public int getAsInt(String tag) throws ClassCastException{
		return (int) dataMap.get(tag);
	}
	
	public double getAsDouble(String tag) throws ClassCastException{
		return (double) dataMap.get(tag);
	}
	
	public String getAsString(String tag) throws ClassCastException, NullPointerException{
		return String.valueOf(dataMap.get(tag));
	}
	
	public String getAsString(String tag, String defaultValue) throws ClassCastException, NullPointerException{
		String value = null;
		if(dataMap.containsKey(tag))
			value = String.valueOf(dataMap.get(tag));
		else
			value = defaultValue;
		return value;
	}
	
	public LocalDateTime getAsDateTime(String tag) throws ClassCastException, NullPointerException{
		return (LocalDateTime) dataMap.get(tag);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String key : dataMap.keySet()) {
			sb.append("[");
			sb.append(key);
			sb.append(":");
			sb.append(getAsString(key));
			sb.append("] ");
		}
		
		return sb.toString();
	}
}
