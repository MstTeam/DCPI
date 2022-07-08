package net.mst.requests;

public final class Response<T extends Object> {
	
	// private boolean executed = true;
	
	private T t;
	
	Response(T t) {
		
		this.t = t;
		
	}
	
	public T getReturned() {
		
		return t;
		
	}
	
}