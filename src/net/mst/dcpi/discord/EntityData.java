package net.mst.dcpi.discord;

import net.mst.dcpi.discord.entities.ClientInstance;
import net.mst.json.JsonObject;
import net.mst.requests.EntityResponse;

public class EntityData {
	
	private JsonObject jsonObject;
	private ClientInstance clientInstance;
	
	private String id;
	
	public EntityData(JsonObject JsonObject, ClientInstance ClientInstance, String ID) {
		
		this.jsonObject = JsonObject;
		this.clientInstance = ClientInstance;
		this.id = ID;
		
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
			
			return (T) EntityResponse.CACHE;
			
		}
		
		if(jsonObject.contains(Key)) {
				
			return (T) (jsonObject.get(Key));
					
		}
		
		return null;
		
	}

}
