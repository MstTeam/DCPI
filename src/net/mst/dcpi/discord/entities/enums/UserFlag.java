package net.mst.dcpi.discord.entities.enums;

public enum UserFlag {
	
	STAFF (0),
	PARTNER (1),
	HYPESQUAD_EVENTS (2),
	BUG_HUNTER_LEVEL_1 (3),
	BUG_HUNTER_LEVEL_2 (14),
	TEAM_PSEUDO_USER (10),
	VERIFIED_BOT (16),
	EARLY_VERIFIED_DEVELOPER (17),
	CERTIFIED_MODERATOR (18),
	BOT_HTTP_INTERACTIONS (19),
	HYPESQUAD_BRAVERY (6),
	HYPESQUAD_BRILLIANCE (7),
	HYPESQUAD_BALANCE (8),
	PREMIUM_EARLY_SUPPORTER (9),
	INVALID (20);
	
	private Integer id;
	
	private UserFlag(Integer ID) {
		
		this.id = ID;
		
	}
	
	public static UserFlag ofValue(Integer Value) {
		
		for(UserFlag p : UserFlag.values()) {
			
			if(p.id.equals(Value)) {
				
				return p;
				
			}
			
		}
		
		return null;
		
	}

}
