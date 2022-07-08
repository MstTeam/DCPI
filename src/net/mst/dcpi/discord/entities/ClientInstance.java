package net.mst.dcpi.discord.entities;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import net.mst.dcpi.discord.app.enums.ApiVersion;
import net.mst.dcpi.discord.app.gateway.Gateway;
import net.mst.dcpi.discord.app.gateway.Shard;
import net.mst.requests.Request;
import net.mst.requests.RequestManager;
import net.mst.requests.Response;
import net.mst.utilities.timer.TimerUnit;

public class ClientInstance {
	
	String token = null;
	String name = null;
	
	ApiVersion customApiVersion = ApiVersion.getDefault();
	Shard shard = new Shard().setBrowser("mst").setDevice("mst").setSystem("linux");
	
	HttpClient client = HttpClient.newHttpClient();
	RequestManager manager = new RequestManager().setActionsPerTime(50, 1, TimerUnit.SECONDS);
	
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
		
		//HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://discord.com/api/v" + getApiVersion().getVersion() + Path)).header("Authorization", "Bot " + getToken()).build();
		
		/*try {
			
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			
			System.out.println(response.body());
			
			return response;
			
		} catch (IOException | InterruptedException e) {}
		*/
		
	}
	
}
