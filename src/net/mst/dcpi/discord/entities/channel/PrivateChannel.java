package net.mst.dcpi.discord.entities.channel;

import net.mst.dcpi.discord.entities.ClientInstance;
import net.mst.json.JsonObject;

public class PrivateChannel extends TextChannel {

	public PrivateChannel(String ID, ClientInstance ClientInstance) {
		super(ID, ClientInstance);
	}
	
	public PrivateChannel(JsonObject JsonObject) {
		super(JsonObject);
	}

}
