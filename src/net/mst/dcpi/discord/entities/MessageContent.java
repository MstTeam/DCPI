package net.mst.dcpi.discord.entities;

public class MessageContent {
	
	private String raw;
	
	MessageContent(String RawContent) {
		
		this.raw = RawContent;
		
	}

	public String getRaw() {
		
		return this.raw;
		
	}

}
