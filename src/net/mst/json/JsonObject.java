package net.mst.json;

import java.util.HashMap;

public class JsonObject {

	private HashMap<String, Object> data = new HashMap<>();
	
	public JsonObject() {
		
		
		
	}
	
	public void addValue(String Key, Object Value) {
		
		if(!data.containsKey(Key)) {
			
			data.put(Key, Value);
			
		}
		
	}
	
	public Object get(String Key) {
		
		if(data.containsKey(Key)) {
			
			return data.get(Key);
			
		}
		
		return null;
		
	}
	
	
	
}
