package net.mst.dcpi.discord.app;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import net.mst.json.JsonObject;
import net.mst.json.Parser;
import net.mst.json.Parser;

public class Application extends AccountInstance {
	
	public String getAuthorizationUrl(AuthorizationFlow AuthorizationFlow) {
		
		return null;
		
	}
	
	protected void cache() {
		
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://discord.com/api/v" + customApiVersion.getVersion() + "/oauth2/applications/@me")).header("Authorization", "Bot " + this.token).build();
			
		try {
			
			long l = System.nanoTime();
			
			HttpResponse<String> response = $getterClient.send(request, BodyHandlers.ofString());
			
			long l2 = System.nanoTime();
			
			System.out.println(response.body());
			
			System.out.println("1Time gone: " + (l2 - l));
			
			JsonObject jo = new Parser().parse(response.body());
			
			System.out.println("2Time gone: " + (System.nanoTime() - l2));
			
		} catch (IOException | InterruptedException e) {
			
			e.printStackTrace();
			
		}
		
		startGatewayConnection();
		
	}

}
