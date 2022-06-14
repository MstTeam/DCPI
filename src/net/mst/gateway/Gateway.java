package net.mst.gateway;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.WebSocket;
import java.util.concurrent.ExecutionException;

import net.mst.dcpi.discord.app.AccountInstance;
import net.mst.json.JsonObject;
import net.mst.json.Parser;
import net.mst.dcpi.Object;
import net.mst.dcpi.request.RequestManager;
import net.mst.gateway.enums.GatewayApiVersion;

public class Gateway extends Object {
	
	protected AccountInstance AccountInstance;
	protected WebSocket websocket;
	
	protected GatewayApiVersion GatewayApiVersion;
	
	protected final HttpClient webClient = HttpClient.newHttpClient();
	
	public Gateway(AccountInstance AccountInstance) {
		
		this.GatewayApiVersion = RequestManager.gatewayApiVersion;
		this.AccountInstance = AccountInstance;
		
		try {
			connect();
		} catch (InterruptedException | ExecutionException | URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	public Gateway(AccountInstance AccountInstance, GatewayApiVersion GatewayApiVersion) {
		
		this.GatewayApiVersion = GatewayApiVersion;
		this.AccountInstance = AccountInstance;
		
		try {
			connect();
		} catch (InterruptedException | ExecutionException | URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	public String connect() throws InterruptedException, ExecutionException, URISyntaxException {
		
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://discord.com/api/v" + this.AccountInstance.getApiVersion().getVersion() + "/gateway/bot")).header("Authorization", "Bot " + this.AccountInstance.getToken()).build();
		
		try {
			
			HttpResponse<String> response = $getterClient.send(request, BodyHandlers.ofString());
			
			System.out.println(response.body());
			
			JsonObject jo = new Parser().parse(response.body());
			
			if(jo.contains("session_start_limit")) {
				
				JsonObject subjo = jo.getObject("session_start_limit");
				
				if(subjo.contains("remaining")) {
					
					if(subjo.getInteger("remaining") > 0) {
						
						websocket =  webClient.newWebSocketBuilder().buildAsync(new URI("wss://gateway.discord.gg/?v=" + this.GatewayApiVersion.getVersion() + "&encoding=json"), new GatewayManager(this)).get();
						
					}
					
				}
				
			}
			
			return null;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

}
