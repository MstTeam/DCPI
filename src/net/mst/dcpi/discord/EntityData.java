package net.mst.dcpi.discord;

import java.net.http.HttpResponse;

import net.mst.dcpi.discord.entities.ClientInstance;
import net.mst.json.JsonObject;
import net.mst.json.Parser;

public class EntityData {
	
	private JsonObject jsonObject;
	private ClientInstance clientInstance;
	
	private String id;
	
	private Object[] parameters;
	private RouteType routeType;
	
	public EntityData(JsonObject JsonObject, ClientInstance ClientInstance, String ID) {
		
		this.jsonObject = JsonObject;
		this.clientInstance = ClientInstance;
		this.id = ID;
		
	}
	
	public EntityData setRoute(RouteType RouteType, Object... Parameters) {
		
		this.parameters = Parameters;
		this.routeType = RouteType;
		
		if(Parameters.length < 1) {
			
			this.parameters = new Object[1];
			this.parameters[0] = (Object) id;
			
		}
		
		return this;
		
	}
	
	public String getId() {
		
		return this.id;
		
	}
	
	public EntityData updateJson(JsonObject JsonObject) {
		
		this.jsonObject = JsonObject;
				
		return this;
		
	}
	
	public ClientInstance getClientInstance() {
		
		return this.clientInstance;
		
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Object> T get(String Key) {
		
		if(jsonObject == null) {
			
			this.jsonObject = cache();
			
		}
		
		if(jsonObject.contains(Key)) {
				
			return (T) (jsonObject.get(Key));
					
		}
		
		return null;
		
	}
	
	public JsonObject cache() {
		
		HttpResponse<String> response = clientInstance.sendGetRequest(routeType.getRoute(parameters));
		System.out.println(response.body());
		return new Parser().parse(response.body());
		
	}

}
