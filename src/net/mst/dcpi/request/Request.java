package net.mst.dcpi.request;

import java.net.http.WebSocket;

public class Request {
	
	private WebSocket webSocket = null;
	private String command = "";
	
	public Request(WebSocket WebSocket, String Command) {
		
		this.webSocket = WebSocket;
		this.command = Command;
		
	}
	
	public WebSocket getWebSocket() {
		
		return this.webSocket;
		
	}
	
	public String getCommand() {
		
		return this.command;
		
	}

}
