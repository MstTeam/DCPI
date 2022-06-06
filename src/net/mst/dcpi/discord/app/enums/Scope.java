package net.mst.dcpi.discord.app.enums;

public enum Scope {

	ACTIVITIES_WRITE ("activites.read", true, true),
	ACTIVITIES_READ ("activities.write", true, true),
	
	APPLICATIONS_BUILDS_READ ("applications.builds.read", false, true),
	APPLICATIONS_BUILDS_UPLOAD ("applications.builds.upload", true, true),
	APPLICATIONS_COMMANDS ("applications.commands", false),
	APPLICATIONS_COMMANDS_UPDATE ("applications.commands.update", false, true),
	APPLICATIONS_COMMANDS_PERMISSIONS_UPDATE ("applications.commands.permissions.update", false, true),
	APPLICATIONS_ENTITLEMENTS ("applications.entitlements", false, true),
	APPLICATIONS_STORE_UPDATE ("applications.store.update", false, true),
	
	BOT ("bot", false),
	CONNECTIONS ("connections", false, true),
	DM_CHANNELS_READ ("dm_channels.read", true, true),
	EMAIL ("email", false, true),
	GDM_JOIN ("gdm.join", false, true),
	
	GUILDS ("guilds", false, true),
	GUILDS_JOIN ("guilds.join", false, true),
	GUILDS_MEMBERS_READ ("guilds.members.read", false, true),
	IDENTIFY ("identify", false, true),
	MESSAGES_READ ("messages.read", false, true),
	RELATIONSHIPS_READ ("relationships.read", true, true),
	
	RPC ("rpc", true, true),
	RPC_ACTIVITES_WRITE ("rpc.activites.write", true, true),
	RPC_NOTIFICATIONS_READ ("rpc.notifications.read", true, true),
	RPC_VOICE_WRITE ("rpc.voice.write", true, true),
	RPC_VOICE_READ ("rpc.voice.read", true, true),
	
	VOICE ("voice", true, true),
	WEBHOOK_INCOMING ("webhook.incoming", false, true);
	
	private String id = null;
	private boolean approvalRequired = false;
	private boolean redirectURIRequired = false;
	
	private Scope(String Id) {
		
		this.id = Id;
		
	}
	
	private Scope(String s, Boolean requiresApproval) {
		
		this.id = s;
		this.approvalRequired = requiresApproval;
		
	}
	
	private Scope(String s, Boolean requiresApproval, Boolean requiresRedirectURI) {
		
		this.id = s;
		this.approvalRequired = requiresApproval;
		this.redirectURIRequired = requiresRedirectURI;
		
	}
	
	public Boolean requiresApproval() {
		
		return this.approvalRequired;
		
	}
	
	public String getName() {
		
		return this.id;
		
	}
	
	public Boolean requiresRedirectURI() {
		
		return this.redirectURIRequired;
		
	}
	
	public Scope ofName(String Name) {
		
		for(Scope s : Scope.values()) {
			
			if(s.id.equals(Name)) {
				
				return s;
				
			}
			
		}
		
		return null;
		
	}
	
}
