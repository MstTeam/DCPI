package net.mst.json;

import java.util.HashMap;
import java.util.Set;

public class JsonObject {

	private HashMap<String, Object> data;
	
	public JsonObject() {
		
		data = new HashMap<String, Object>();
		
	}
	
	public HashMap<String, Object> hash() {
		
		return this.data;
		
	}
	
	public void addValue(String Key, Object Value) {
		
		if(!data.containsKey(Key)) {
			
			data.put(Key, Value);
			
		}
		
	}
	
	public void removeValue(String Key) {
		
		if(data.containsKey(Key)) {
			
			data.remove(Key);
			
		}
		
	}
	
	public Boolean contains(String Key) {
		
		if(data.containsKey(Key)) {
			
			return true;
			
		}

		return false;
		
	}
	
	public Set<String> getKeys() {
		
		return data.keySet();
		
	}
	
	public boolean isEmpty() {
		
		if(data.isEmpty()) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	public Object get(String Key) {
		
		if(data.containsKey(Key)) {
			
			return data.get(Key);
			
		}
		
		return null;
		
	}
	
	public String getString(String Key) {
		
		if(data.containsKey(Key)) {
			
			return String.valueOf(data.get(Key));
			
		}
		
		return null;
		
	}
	
	public Integer getInteger(String Key) {
		
		if(data.containsKey(Key)) {
			
			return (Integer) data.get(Key);
			
		}
		
		return null;
		
	}
	
	public Long getLong(String Key) {
		
		if(data.containsKey(Key)) {
			
			return (Long) data.get(Key);
			
		}
		
		return null;
		
	}
	
	public Boolean getBoolean(String Key) {
		
		if(data.containsKey(Key)) {
			
			return (Boolean) data.get(Key);
			
		}
		
		return null;
		
	}
	
	public JsonObject getObject(String Key) {
		
		if(data.containsKey(Key)) {
			
			return (JsonObject) data.get(Key);
			
		}
		
		return null;
		
	}

	public JsonArray getArray(String Key) {
	
		if(data.containsKey(Key)) {
		
			return (JsonArray) data.get(Key);
		
		}
	
		return null;
	
	}
	
	public void addaptive(JsonObject JsonObject) {
		
		if(!JsonObject.isEmpty()) {
			
			JsonObject.getKeys().forEach(key -> {
				
				this.data.put(key, JsonObject.get(key));
				
			});
			
		}
		
	}
	
}
