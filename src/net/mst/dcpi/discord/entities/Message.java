package net.mst.dcpi.discord.entities;

import net.mst.dcpi.discord.EntityData;
import net.mst.dcpi.discord.RouteType;
import net.mst.json.JsonObject;

public class Message {
	
	String id = "0";
	String channelId = "0";
	EntityData data;
	
	ClientInstance client;
	
	public Message(String MessageId, String ChannelId, ClientInstance ClientInstance) {
		
		this.id = MessageId;
		this.channelId = ChannelId;
		this.client = ClientInstance;
		
		System.out.println(ChannelId + " / " + MessageId);
		
		createEntityData();
		
	}
	
	Message(JsonObject Object, ClientInstance ClientInstance) {
		
		this.client = ClientInstance;
		this.id = Object.getString("id");
		this.channelId = Object.getString("channel_id");
		
		createEntityData();
		
	}
	
	private void createEntityData() {
		
		this.data = new EntityData(null, client, id).setRoute(RouteType.GET_CHANNEL_MESSAGE, channelId, id);
		
	}
	 
	public String getId() {
		
		return this.id;
		
	}
	
	public void update() {
		
		data.cache();
		
	}
	
	// Methods
	
	public MessageChannel getChannel() {
		
		return new BaseMessageChannel(data.get("channel_id"), client);
		
	}
	
	public String getRawContent() {
		
		return data.get("content");
		
	}
	
	public MessageContent getContent() {
		
		return new MessageContent(data.get("content"));
		
	}
	
	public boolean isTTS() {
		
		return data.get("tts");
		
	}
	
	public boolean isPinned() {
		
		return data.get("pinned");
		
	}
	
	public boolean mentionsEveryone() {
		
		return data.get("mention_everyone");
		
	}
	
	public String getJumpUrl() {
		
		return "https://discord.com/";
		
	}

}
