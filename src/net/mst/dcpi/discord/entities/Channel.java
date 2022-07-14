package net.mst.dcpi.discord.entities;

import net.mst.dcpi.discord.EntityData;
import net.mst.dcpi.discord.entities.enums.channel.ChannelType;

public interface Channel extends IEntity {
	
	private EntityData getData() {
		
		return getClientInstance().channelCache.getEntityById(getId());
		
	}
	
	// Methods
	
	public default ChannelType getChannelType() {
		
		return ChannelType.ofValue(getData().get("type"));
		
	}

}
