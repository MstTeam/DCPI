package net.mst.dcpi.discord.entities.enums.channel;

public enum ChannelType {

	//TextChannel: 1, 3, 0, 5, 10, 11, 12
	//
	PRIVATE (1),
	GROUP (3),
	
	GUILD_TEXT (0),
	GUILD_VOICE (2),
	GUILD_CATEGORY (4),
	GUILD_NEWS (5),
	GUILD_NEWS_THREAD (10),
	GUILD_PUBLIC_THREAD (11),
	GUILD_PRIVATE_THREAD (12),
	GUILD_STAGE_VOICE (13),
	GUILD_DIRECTORY (14),
	GUILD_FORUM (15);
	
	private Integer id;
	
	private ChannelType(Integer ID) {
		
		this.id = ID;
		
	}
	
	public static ChannelType ofValue(Integer Value) {
		
		for(ChannelType p : ChannelType.values()) {
			
			if(p.id.equals(Value)) {
				
				return p;
				
			}
			
		}
		
		return null;
		
	}

}
