package net.mst.dcpi.discord.entities.enums.user;

public enum ImageType {
	
	CUSTOM_EMOJI ("emojis/%s"),
	GUILD_ICON ("icons/%s/%s"),
	GUILD_SPLASH ("splashes/%s/%s"),
	GUILD_DISCOVERY_SPLASH ("discovery-splashes/%s/%s"),
	GUILD_BANNER ("banners/%s/%s"),
	USER_BANNER ("banners/%s/%s"),
	DEFAULT_USER_AVATAR ("embed/avatars/%s"),
	USER_AVATAR ("avatars/%s/%s"),
	GUILD_MEMBER_AVATAR ("guilds/%s/users/%s/avatars/%s"),
	APPLICATION_ICON ("app-icons/%s/%s"),
	APPLICATION_COVER ("app-icons/%s/%s"),
	APPLICATION_ASSET ("app-assets/%s/%s"),
	ACHIEVEMENT_ICON ("app-assets/%s/achievements/%s/icons/%s"),
	STICKER_PACK_BANNER ("app-assets/710982414301790216/store/%s"),
	TEAM_ICON ("team-icons/%s/%s"),
	STICKER ("stickers/%s"),
	ROLE_ICON ("role-icons/%s/%s"),
	GUILD_SCHEDULED_EVENT_COVER ("guild-events/%s/%s"),
	GUILD_MEMBER_BANNER ("guilds/%s/users/%s/banners/%s"),
	AVATAR_DECORATIONS ("avatar-decorations/%s/%s");
	
	private String path;
	
	private ImageType(String Path) {
		
		this.path = Path;
		
	}
	
	public String getPath(Object... Parameters) {
		
		return String.format(this.path, Parameters);
		
	}

}
