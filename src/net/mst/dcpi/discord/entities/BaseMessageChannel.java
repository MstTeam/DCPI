package net.mst.dcpi.discord.entities;

import net.mst.dcpi.discord.EntityData;

public class BaseMessageChannel implements MessageChannel {
	
	private String id;
	private ClientInstance client;

	BaseMessageChannel(String Id, ClientInstance ClientInstance) {
		
		this.id = Id;
		this.client = ClientInstance;
		
	}
	
	BaseMessageChannel(EntityData EntityData) {
		
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

}