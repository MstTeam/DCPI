package net.mst.dcpi.discord.entities;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.function.Supplier;

import net.mst.dcpi.discord.Cache;
import net.mst.dcpi.discord.EntityData;
import net.mst.dcpi.discord.app.enums.ApiVersion;
import net.mst.dcpi.discord.app.gateway.Gateway;
import net.mst.dcpi.discord.app.gateway.Shard;
import net.mst.json.JsonObject;
import net.mst.json.Parser;
import net.mst.requests.Request;
import net.mst.requests.RequestManager;
import net.mst.utilities.timer.TimerUnit;

public class ClientInstance {
	
	String token = null;
	String name = null;
	
	ApiVersion customApiVersion = ApiVersion.getDefault();
	Shard shard = new Shard().setBrowser("mst").setDevice("mst").setSystem("linux");
	
	HttpClient client = HttpClient.newHttpClient();
	RequestManager manager = new RequestManager().setActionsPerTime(50, 1, TimerUnit.SECONDS);
	
	private Gateway gateway = null;
	
	// Cache
	
	Cache<EntityData> channelCache = new Cache<EntityData>(this); 
	
	ClientInstance() {}
	
	public String getToken() {
		
		return this.token;
		
	}
	
	public String getName() {
		
		return this.name;
		
	}
	
	public ApiVersion getApiVersion() {
		
		return this.customApiVersion;
		
	}
	
	void startGatewayConnection() {
		
		this.gateway = new Gateway(this);
		
	}
	
	public Shard getShard() {
		
		return this.shard;
		
	}
	
	public HttpResponse<String> sendGetRequest(String Path) {
		
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://discord.com/api/v" + getApiVersion().getVersion() + Path)).header("Authorization", "Bot " + getToken()).build();
		
		Request<HttpResponse<String>> requestt = new Request<HttpResponse<String>>() {

			@Override
			public HttpResponse<String> setAction() {
				
				try {
					return client.send(request, BodyHandlers.ofString());
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		};
		
		return manager.instantRequest(requestt).getReturned();
		
	}
	
	public <T> Supplier<HttpResponse<String>> sendPostRequest(String Path, JsonObject JsonObject) {
		
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://discord.com/api/v" + getApiVersion().getVersion() + Path)).header("Authorization", "Bot " + getToken()).POST(BodyPublishers.ofString(new Parser().parse(JsonObject))).header("Content-Type", "application/json").build();
		
		Supplier<HttpResponse<String>> supplier = () -> {try {
			return client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			return null;
		}};
		
		return supplier;
		
		/*Request<HttpResponse<String>> response = new Request<HttpResponse<String>>() {

			@Override
			public HttpResponse<String> setAction() {
				
				try {
					return client.send(request, BodyHandlers.ofString());
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		};
		
		return new RequestAction<HttpResponse<String>>(manager, response);*/
		
	}
	
}
