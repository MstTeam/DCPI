package net.mst.dcpi.request;

import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import net.mst.dcpi.discord.app.enums.ApiVersion;
import net.mst.gateway.enums.GatewayApiVersion;

public class RequestManager {
	
	private Map<WebSocket, Stack<Request>> requests = new HashMap<>();
	
	protected HttpClient $getterClient = HttpClient.newHttpClient();
	
	public static ApiVersion restApiVersion = ApiVersion.VERSION_10;
	public static GatewayApiVersion gatewayApiVersion = GatewayApiVersion.VERSION_9;
	
	

}
