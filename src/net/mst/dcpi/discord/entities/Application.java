package net.mst.dcpi.discord.entities;

import java.net.http.HttpResponse;

import net.mst.dcpi.discord.ApiException;
import net.mst.dcpi.discord.app.AuthorizationFlow;
import net.mst.dcpi.discord.enums.Error;
import net.mst.dcpi.discord.enums.ResponseType;
import net.mst.json.JsonObject;
import net.mst.json.Parser;

public class Application extends ClientInstance {
	
	public String getAuthorizationUrl(AuthorizationFlow AuthorizationFlow) {
		
		return null;
		
	}
	
	void cache() {
		
		HttpResponse<String> response = sendGetRequest("/oauth2/applications/@me");
			
		JsonObject jo = new Parser().parse(response.body());
			
		System.out.println(jo.hash().toString());
		System.out.println(response.statusCode());
			
		if(response.statusCode() != 200) {
				
			throw new ApiException(Error.ofData(Integer.valueOf(response.statusCode()), ResponseType.HTTP_ERROR), "Bot Authorization failed with error code");
				
		}
		
		//startGatewayConnection();
		
	}

}
