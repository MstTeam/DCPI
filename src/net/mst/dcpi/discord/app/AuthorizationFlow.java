package net.mst.dcpi.discord.app;

import java.util.ArrayList;
import java.util.List;

import net.mst.dcpi.discord.app.enums.Scope;

public class AuthorizationFlow {
	
	private String clientId = null;
	private List<Scope> scopes = new ArrayList<Scope>();
	private String guildId = null;
	private boolean disableGuildSelect = false;
	
	public AuthorizationFlow(String ClientID, List<Scope> Scopes, String PreGuildID, Boolean DisableGuildSelection) {
		
		this.clientId = ClientID;
		this.scopes = Scopes;
		this.guildId = PreGuildID;
		this.disableGuildSelect = DisableGuildSelection;
		
	}
	
	

}
