package net.mst.dcpi.discord.entities;

import net.mst.dcpi.discord.EntityData;

public class BaseChannel implements Channel {
	
	private String id;
	private ClientInstance client;

	BaseChannel(String Id, ClientInstance ClientInstance) {
		
		this.id = Id;
		this.client = ClientInstance;
		
	}
	
	BaseChannel(EntityData EntityData) {
		
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