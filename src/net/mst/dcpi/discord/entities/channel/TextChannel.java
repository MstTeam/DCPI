package net.mst.dcpi.discord.entities.channel;

import net.mst.dcpi.discord.entities.ClientInstance;
import net.mst.json.JsonObject;

public class TextChannel extends Channel {

	public TextChannel(String ID, ClientInstance ClientInstance) {
		super(ID, ClientInstance);
	}
	
	public TextChannel(JsonObject JsonObject) {
		super(JsonObject);
	}
	
	public String getTopic() {
		
		return get("topic");
		
	}
	
	public String getLastMessageId() {
		
		return get("last_message_id");
		
	}

}
