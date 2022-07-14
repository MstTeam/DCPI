package net.mst.dcpi.discord.entities.enums.user;

public enum ImageType {
	
	CUSTOM_EMOJI ("emojis/%s"),
	GUILD_ICON ("icons/%s/%s"),
	GUILD_SPLASH (""),
	GUILD_DISCOVERY_SPLASH (""),
	GUILD_BANNER (""),
	DEFAULT_USER_AVATAR (""),
	USER_AVATAR (""),
	GUILD_MEMBER_AVATAR (""),
	APPLICATION_ICON (""),
	APPLICATION_COVER (""),
	APPLICATION_ASSET (""),
	ACHIEVEMENT_ICON (""),
	STICKER_PACK_BANNER (""),
	TEAM_ICON (""),
	STICKER (""),
	ROLE_ICON (""),
	GUILD_SCHEDULED_EVENT_COVER (""),
	GUILD_MEMBER_BANNER ("");
	
	private ImageType(String Path) {
		
		
		
	}

}
