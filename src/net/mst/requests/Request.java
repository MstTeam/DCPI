package net.mst.requests;

public abstract class Request<T extends Object> {
	
	// private boolean executed = false;
	
	public Request() {
		
	}
	
	public Response<T> perform() {
		
		return new Response<T>(setAction());
		
	}
	
	public abstract T setAction();

}