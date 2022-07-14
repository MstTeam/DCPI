package net.mst.dcpi.discord.entities;

import net.mst.dcpi.discord.EntityData;
import net.mst.json.JsonArray;

public class PrivateChannel implements MessageChannel {
	
	private String id;
	private ClientInstance client;

	PrivateChannel(String Id, ClientInstance ClientInstance) {
		
		this.id = Id;
		this.client = ClientInstance;
		
	}
	
	PrivateChannel(EntityData EntityData) {
		
		this.id = EntityData.getId();
		this.client = EntityData.getClientInstance();
		
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public ClientInstance getClientInstance() {
		return this.client;
	}

	private EntityData getData() {
		
		return getClientInstance().channelCache.getEntityById(getId());
		
	}
	
	// Methods
	
	public User getRecipient() {
		
		JsonArray array = getData().get("recipients");
		
		if(array != null) {
			
			return new User(array.getObjects().get(0), getClientInstance());
			
		}
		
		return null;
		
	}

}
