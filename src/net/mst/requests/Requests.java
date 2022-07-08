package net.mst.requests;

import java.util.HashMap;
import java.util.Map;

public class Requests {
	
	private Map<Integer, Request<?>> list = new HashMap<Integer, Request<?>>();
	
	public Requests() {
		
	}
	
	public Request<?> getLast() {
		
		return list.get(list.size());
		
	}
	
	public Request<?> getFirst() {
		
		if(list.isEmpty()) {
			
			return null;
			
		}
		
		return list.get(1);
		
	}
	
	public Requests removeLast() {
		
		removeRequest(list.size());
		
		return this;
		
	}
	
	public Requests addRequest(Request<?> Request) {
		
		list.put(list.size() + 1, Request);
		return this;
		
	}
	
	public Requests removeRequest(Integer Position) {
		
		if(list.containsKey(Position)) {
			
			list.remove(Position);
			
		}
		
		return this;
		
	}
	
	public boolean isEmpty() {
		
		return this.list.isEmpty();
		
	}

}
