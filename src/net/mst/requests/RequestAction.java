package net.mst.requests;

public class RequestAction<T> {
	
	RequestManager requestManager;
	Request<T> request;
	
	public RequestAction(RequestManager RequestManager, Request<T> Request) {
		
		this.requestManager = RequestManager;
		this.request = Request;
		
	}
	
	public void queue() {
		
		requestManager.queueRequest(request);
		
	}
	
	public T complete() {
		
		return requestManager.instantRequest(request).getReturned();
		
	}
	
}
