package net.mst.dcpi.discord.app;

import java.net.http.HttpClient;

import net.mst.dcpi.discord.app.enums.ApiVersion;
import net.mst.dcpi.discord.app.gateway.Gateway;
import net.mst.dcpi.discord.app.gateway.Shard;

public class ClientInstance {
	
	String token = null;
	String name = null;
	
	ApiVersion customApiVersion = null;
	Shard shard = null;
	
	HttpClient client = HttpClient.newHttpClient();
	
	private HttpClient httpClient;
	
	private Gateway gateway = null;
	
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
	
	protected void startGatewayConnection() {
		
		this.gateway = new Gateway(this);
		
	}
	
	public Shard getShard() {
		
		return this.shard;
		
	}
	
}
