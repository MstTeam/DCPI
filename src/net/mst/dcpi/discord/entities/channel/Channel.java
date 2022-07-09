package net.mst.dcpi.discord.entities.channel;

import java.net.http.HttpResponse;

import net.mst.dcpi.discord.entities.ClientInstance;
import net.mst.json.JsonObject;
import net.mst.json.Parser;

public class Channel {
	
	String id = "0";
	JsonObject data;
	
	ClientInstance client;
	
	public Channel(String ID, ClientInstance ClientInstance) {
		
		this.id = ID;
		this.client = ClientInstance;
		
	}
	
	public Channel(JsonObject Data) {
		
		this.data = Data;
		this.id = data.getString("id");
		
	}
	
	public String getId() {
		
		return this.id;
		
	}
	
	void cache() {
		
		HttpResponse<String> response = client.sendGetRequest("/channels/" + this.id);
		
		data = new Parser().parse(response.body());
		
		System.out.println(response.body());
		
	}
	
	public void update() {
		
		cache();
		
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends Object> T get(String Key) {
		
		if(data == null) {
			
			cache();
			
		}
		
		if(data.contains(Key)) {
				
			return (T) (data.get(Key));
					
		}
		
		return null;
		
	}
	
	// Methods
	
	public String getName() {
		
		return get("name");
		
	}

}
