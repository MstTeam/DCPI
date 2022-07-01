package net.mst.dcpi.discord.app.gateway.enums;
import java.util.List;

public enum GatewayIntent {
	
	ALL(-1),
	GUILDS (0),
	GUILD_MEMBERS (1),
	GUILD_BANS (2),
	GUILD_EMOJIS_AND_STICKERS (3),
	GUILD_INTEGRATIONS (4),
	GUILD_WEBHOOKS (5),
	GUILD_INVITES (6),
	GUILD_VOICE_STATES (7),
	GUILD_PRESENCES (8),
	GUILD_MESSAGES (9),
	GUILD_MESSAGE_REACTIONS (10),
	GUILD_MESSAGE_TYPING (11),
	DIRECT_MESSAGES (12) ,
	DIRECT_MESSAGE_REACTIONS (13),
	DIRECT_MESSAGE_TYPING (14),
	MESSAGE_CONTENT (15),
	GUILD_SCHEDULED_EVENTS (16);
	
	private int id;
	
	private GatewayIntent(int s) {
		
		this.id = s;
		
	}
	
	public static GatewayIntent ofId(int ID) {
		
		for(GatewayIntent s : GatewayIntent.values()) {
			
			if(s.id == ID) {
				
				return s;
				
			}
			
		}
		
		return null;
		
	}
	
	public static String getIntentInteger(List<GatewayIntent> Intents) {
		
		String s = "";
		
		if(!Intents.isEmpty()) {
			
			for(int i = 0; i <= 16; i++) {
				
				if(Intents.contains(ofId(i))) {
					
					s = "1" + s;
					
				}else {
					
					s = "0" + s;
					
				}
				
			}
			
		}else {
			
			s = "1111111111111111";
			
		}
		
		if(Intents.contains(GatewayIntent.ALL)) {
			
			s = "1111111111111111";
			
		}
		
		String decimal = String.valueOf(Integer.parseInt(s, 2));
		
		return decimal;
		
	}

}
