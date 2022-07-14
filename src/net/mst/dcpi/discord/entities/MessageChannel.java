package net.mst.dcpi.discord.entities;

import net.mst.dcpi.discord.EntityData;

public interface MessageChannel extends Channel {
	
	private EntityData getData() {
		
		return getClientInstance().channelCache.getEntityById(getId());
		
	}
	
	// Methods
	
	default String getLastMessageId() {
		
		return getData().get("last_message_id");
		
	}

}
