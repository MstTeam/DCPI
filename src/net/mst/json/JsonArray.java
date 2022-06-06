package net.mst.json;

import java.util.ArrayList;
import java.util.List;

public class JsonArray {
	
	private List<Object> data;
	
	public JsonArray() {
		
		data = new ArrayList<Object>();
		
	}
	
	public void addValue(Object Object) {
		
		data.add(Object);
		
	}
	
	public void removeValue(Object Object) {
		
		data.remove(Object);
		
	}
	
	public Object get(Integer Position) {
		
		if(data.size() >= Position - 1) {
			
			return data.get(Position - 1);
			
		}
		
		return null;
		
	}
	
	public List<String> getStrings() {
		
		List<String> tlist = new ArrayList<String>();
		
		data.forEach((Object) -> {tlist.add((String) Object);});
		
		return tlist;
		
	}
	
	public List<Integer> getIntegers() {
		
		List<Integer> tlist = new ArrayList<Integer>();
		
		data.forEach((Object) -> {tlist.add((Integer) Object);});
		
		return tlist;
		
	}
	
	public List<Long> getLongs() {
		
		List<Long> tlist = new ArrayList<Long>();
		
		data.forEach((Object) -> {tlist.add((Long) Object);});
		
		return tlist;
		
	}
	
	public List<Boolean> getBooleans() {
		
		List<Boolean> tlist = new ArrayList<Boolean>();
		
		data.forEach((Object) -> {tlist.add((Boolean) Object);});
		
		return tlist;
		
	}
	
	public List<JsonObject> getObjects() {
		
		List<JsonObject> tlist = new ArrayList<JsonObject>();
		
		data.forEach((Object) -> {tlist.add((JsonObject) Object);});
		
		return tlist;
		
	}
	
	public List<JsonArray> getArrays() {
		
		List<JsonArray> tlist = new ArrayList<JsonArray>();
		
		data.forEach((Object) -> {tlist.add((JsonArray) Object);});
		
		return tlist;
		
	}
	
	public List<Object> get() {
		
		return data;
		
	}

}
