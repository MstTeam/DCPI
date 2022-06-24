package net.mst.dcpi.discord.app;

import net.mst.dcpi.Object;
import net.mst.dcpi.discord.app.enums.ApiVersion;
import net.mst.gateway.Gateway;
import net.mst.gateway.Shard;

public class AccountInstance extends Object {
	
	protected String token = null;
	protected String name = null;
	protected ApiVersion customApiVersion = null;
	protected Shard shard = null;
	
	protected Gateway gateway = null;
	
	public String getToken() {
		
		return this.token;
		
	}
	
	public String getName() {
		
		return this.name;
		
	}
	
	public ApiVersion getApiVersion() {
		
		return this.customApiVersion;
		
	}
	
	protected void startGatewayConnection() {
		
		this.gateway = new Gateway(this);
		
	}
	
	public Shard getShard() {
		
		return this.shard;
		
	}
}
